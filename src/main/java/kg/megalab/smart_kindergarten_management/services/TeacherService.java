package kg.megalab.smart_kindergarten_management.services;

import kg.megalab.smart_kindergarten_management.models.dto.TeacherDto;
import org.springframework.data.domain.Page;

public interface TeacherService {

    TeacherDto createTeacher(TeacherDto teacherDto);

    TeacherDto updateTeacher(Long id, TeacherDto teacherDto);

    TeacherDto findTeacherById(Long id);

    Page<TeacherDto> findAllTeachers(int page, int size);

    void deleteTeacher(Long id);
}