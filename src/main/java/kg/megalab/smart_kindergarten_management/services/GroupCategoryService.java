package kg.megalab.smart_kindergarten_management.services;

import kg.megalab.smart_kindergarten_management.models.dto.GroupCategoryDto;
import java.util.List;

public interface GroupCategoryService {
    GroupCategoryDto createGroupCategory(GroupCategoryDto groupCategoryDto);
    GroupCategoryDto updateGroupCategory(GroupCategoryDto groupCategoryDto, Long id);
    GroupCategoryDto findGroupCategoryById(Long id);
    List<GroupCategoryDto> findAllGroupCategory(int pageNo, int pageSize);
    void deleteGroupCategory(Long id);
}