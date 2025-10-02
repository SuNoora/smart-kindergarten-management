package kg.megalab.smart_kindergarten_management.services.impl;

import kg.megalab.smart_kindergarten_management.exceptions.ConflictException;
import kg.megalab.smart_kindergarten_management.exceptions.LogicException;
import kg.megalab.smart_kindergarten_management.exceptions.NotFoundException;
import kg.megalab.smart_kindergarten_management.mappers.GroupMapper;
import kg.megalab.smart_kindergarten_management.models.Group;
import kg.megalab.smart_kindergarten_management.models.GroupCategory;
import kg.megalab.smart_kindergarten_management.models.dto.GroupDto;
import kg.megalab.smart_kindergarten_management.repositories.GroupCategoryRepo;
import kg.megalab.smart_kindergarten_management.repositories.GroupRepo;
import kg.megalab.smart_kindergarten_management.repositories.TeacherRepo;
import kg.megalab.smart_kindergarten_management.services.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepo groupRepo;
    private final TeacherRepo teacherRepo;
    private final GroupCategoryRepo groupCategoryRepo;
    private final GroupMapper groupMapper;

    @Override
    @Transactional
    public GroupDto createGroup(GroupDto groupDto) {
        // Проверяем уникальность названия группы
        if (groupRepo.existsByNameIgnoreCase(groupDto.getName())) {
            throw new ConflictException("Группа с таким названием уже существует!");
        }

        validateRelatedEntities(groupDto);

        GroupCategory category = groupCategoryRepo.findById(groupDto.getGroupCategoryId())
                .orElseThrow(() -> new NotFoundException("Категория не найдена"));

        Group group = groupMapper.groupDtoToGroup(groupDto);
        group.setGroupCategory(category); // ⚡ устанавливаем категорию

        Group savedGroup = groupRepo.save(group);

        return groupMapper.groupToGroupDto(savedGroup);
    }

    @Override
    @Transactional
    public GroupDto updateGroup(Long id, GroupDto groupDto) {
        Group existingGroup = groupRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Группа не найдена!"));

        // Проверяем уникальность названия (исключая текущую группу)
        if (!existingGroup.getName().equals(groupDto.getName()) &&
                groupRepo.existsByNameIgnoreCase(groupDto.getName())) {
            throw new ConflictException("Группа с таким названием уже существует!");
        }

        groupMapper.updateGroupFromDto(groupDto, existingGroup);
        Group updatedGroup = groupRepo.save(existingGroup);

        return groupMapper.groupToGroupDto(updatedGroup);
    }

    @Override
    public GroupDto findGroupById(Long id) {
        Group group = groupRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Группа не найдена!"));

        return groupMapper.groupToGroupDto(group);
    }

    @Override
    public Page<GroupDto> findAllGroups(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new LogicException("Ошибка параметров пагинации!");
        }

        // Сортировка по категории группы
        Pageable pageable = PageRequest.of(page, size, Sort.by("groupCategory.name", "name"));
        Page<Group> groups = groupRepo.findAll(pageable);

        return groups.map(groupMapper::groupToGroupDto);
    }

    @Override
    @Transactional
    public void deleteGroup(Long id) {
        Group group = groupRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Группа не найдена!"));

        // Проверяем, есть ли зачисленные дети в группе
        if (group.getEnrollments() != null && !group.getEnrollments().isEmpty()) {
            throw new ConflictException("Нельзя удалить группу, в которой есть зачисленные дети!");
        }

        groupRepo.delete(group);
    }

    private void validateRelatedEntities(GroupDto groupDto) {
        // Проверяем существование учителя
        if (!teacherRepo.existsById(groupDto.getTeacherId())) {
            throw new NotFoundException("Учитель не найден!");
        }

        // Проверяем существование няни
        if (!teacherRepo.existsById(groupDto.getNannyId())) {
            throw new NotFoundException("Няня не найдена!");
        }

        // Проверяем существование категории группы
        if (!groupCategoryRepo.existsById(groupDto.getGroupCategoryId())) {
            throw new NotFoundException("Категория группы не найдена!");
        }
    }
}