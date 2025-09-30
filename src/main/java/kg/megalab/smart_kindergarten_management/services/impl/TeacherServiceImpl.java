package kg.megalab.smart_kindergarten_management.services.impl;

import kg.megalab.smart_kindergarten_management.exceptions.ConflictException;
import kg.megalab.smart_kindergarten_management.exceptions.LogicException;
import kg.megalab.smart_kindergarten_management.exceptions.NotFoundException;
import kg.megalab.smart_kindergarten_management.mappers.TeacherMapper;
import kg.megalab.smart_kindergarten_management.models.Teacher;
import kg.megalab.smart_kindergarten_management.models.dto.TeacherDto;
import kg.megalab.smart_kindergarten_management.repositories.TeacherRepo;
import kg.megalab.smart_kindergarten_management.services.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepo teacherRepo;
    private final TeacherMapper teacherMapper;

    @Override
    @Transactional
    public TeacherDto createTeacher(TeacherDto teacherDto) {
        // Проверяем уникальность по ФИО
        if (teacherRepo.existsByFirstNameAndLastNameIgnoreCase(
                teacherDto.getFirstName(),
                teacherDto.getLastName())) {
            throw new ConflictException("Учитель с таким ФИО уже существует!");
        }

        if (teacherDto.getActive() == null) {
            teacherDto.setActive(true);
        }

        Teacher teacher = teacherMapper.teacherDtoToTeacher(teacherDto);
        Teacher savedTeacher = teacherRepo.save(teacher);

        return teacherMapper.teacherToTeacherDto(savedTeacher);
    }

    @Override
    @Transactional
    public TeacherDto updateTeacher(Long id, TeacherDto teacherDto) {
        Teacher existingTeacher = teacherRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Учитель не найден!"));

        // Проверяем уникальность ФИО (исключая текущего учителя)
        if (!existingTeacher.getFirstName().equals(teacherDto.getFirstName()) ||
                !existingTeacher.getLastName().equals(teacherDto.getLastName()) ||
                !java.util.Objects.equals(existingTeacher.getPatronymic(), teacherDto.getPatronymic())) {

            if (teacherRepo.existsByFirstNameAndLastNameIgnoreCase(
                    teacherDto.getFirstName(),
                    teacherDto.getLastName())) {
                throw new ConflictException("Учитель с таким ФИО уже существует!");
            }
        }

        teacherMapper.updateTeacherFromDto(teacherDto, existingTeacher);
        Teacher updatedTeacher = teacherRepo.save(existingTeacher);

        return teacherMapper.teacherToTeacherDto(updatedTeacher);
    }

    @Override
    public TeacherDto findTeacherById(Long id) {
        Teacher teacher = teacherRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Учитель не найден!"));

        return teacherMapper.teacherToTeacherDto(teacher);
    }

    @Override
    public Page<TeacherDto> findAllTeachers(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new LogicException("Ошибка параметров пагинации!");
        }

        // Создаем Pageable с сортировкой по степени и имени
        Pageable pageable = PageRequest.of(page, size,
                Sort.by("teacherDegree", "firstName", "lastName"));

        // Используем стандартный метод findAll
        Page<Teacher> teachers = teacherRepo.findAll(pageable);

        return teachers.map(teacherMapper::teacherToTeacherDto);
    }


    @Override
    @Transactional
    public void deleteTeacher(Long id) {
        Teacher teacher = teacherRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Учитель не найден!"));

        if (teacher.getGroups() != null && !teacher.getGroups().isEmpty()) {
            throw new ConflictException("Нельзя удалить учителя, который назначен на группу!");
        }

        teacherRepo.delete(teacher);
    }
}