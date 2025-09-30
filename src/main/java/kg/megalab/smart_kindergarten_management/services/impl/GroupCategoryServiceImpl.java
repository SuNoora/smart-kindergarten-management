package kg.megalab.smart_kindergarten_management.services.impl;

import kg.megalab.smart_kindergarten_management.exceptions.ConflictException;
import kg.megalab.smart_kindergarten_management.exceptions.NotFoundException;
import kg.megalab.smart_kindergarten_management.models.dto.GroupCategoryDto;
import kg.megalab.smart_kindergarten_management.models.GroupCategory;
import kg.megalab.smart_kindergarten_management.exceptions.LogicException;
import kg.megalab.smart_kindergarten_management.mappers.GroupCategoryMapper;
import kg.megalab.smart_kindergarten_management.repositories.GroupCategoryRepo;
import kg.megalab.smart_kindergarten_management.services.GroupCategoryService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupCategoryServiceImpl implements GroupCategoryService {

    private final GroupCategoryRepo groupCategoryRepo;
    private final GroupCategoryMapper groupCategoryMapper;

    public GroupCategoryServiceImpl(GroupCategoryRepo groupCategoryRepo) {
        this.groupCategoryRepo = groupCategoryRepo;
        this.groupCategoryMapper = GroupCategoryMapper.INSTANCE;
    }

    @Override
    public GroupCategoryDto createGroupCategory(GroupCategoryDto groupCategoryDto) {
        if (groupCategoryRepo.existsByNameIgnoreCase(groupCategoryDto.getName())) {
            throw new ConflictException("Категория с таким именем уже существует!");
        }

        GroupCategory groupCategory = groupCategoryMapper.groupCategoryDtoToGroupCategory(groupCategoryDto);
        GroupCategory createdGroupCategory = groupCategoryRepo.save(groupCategory);

        return groupCategoryMapper.groupCategoryToGroupCategoryDto(createdGroupCategory);
    }

    @Override
    public GroupCategoryDto updateGroupCategory(GroupCategoryDto groupCategoryDto, Long id) {
        GroupCategory groupCategory = groupCategoryRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Категория не найдена!"));

        if (!groupCategory.getName().equals(groupCategoryDto.getName()) &&
                groupCategoryRepo.existsByNameIgnoreCase(groupCategoryDto.getName())) {
            throw new ConflictException("Категория с таким именем уже существует!");
        }

        groupCategoryMapper.updateGroupCategoryFromDto(groupCategoryDto, groupCategory);
        GroupCategory updated = groupCategoryRepo.save(groupCategory);

        return groupCategoryMapper.groupCategoryToGroupCategoryDto(updated);
    }

    @Override
    public void deleteGroupCategory(Long id) {
        GroupCategory groupCategory = groupCategoryRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Категория не найдена!"));

        groupCategoryRepo.delete(groupCategory);
    }

    @Override
    public GroupCategoryDto findGroupCategoryById(Long id) {
        GroupCategory groupCategory = groupCategoryRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Категория не найдена!"));

        return groupCategoryMapper.groupCategoryToGroupCategoryDto(groupCategory);
    }

    @Override
    public List<GroupCategoryDto> findAllGroupCategory(int pageNo, int pageSize) {
        if (pageNo < 0 || pageSize <= 0) {
            throw new LogicException("Ошибка параметров пагинации!");
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").ascending());
        List<GroupCategory> groupCategories = groupCategoryRepo.findAll(pageable).getContent();

        return groupCategories.stream()
                .map(groupCategoryMapper::groupCategoryToGroupCategoryDto)
                .collect(Collectors.toList());
    }
}