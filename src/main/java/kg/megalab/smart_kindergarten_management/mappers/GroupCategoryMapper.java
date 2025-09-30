package kg.megalab.smart_kindergarten_management.mappers;

import kg.megalab.smart_kindergarten_management.models.GroupCategory;
import kg.megalab.smart_kindergarten_management.models.dto.GroupCategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GroupCategoryMapper {

    GroupCategoryMapper INSTANCE = Mappers.getMapper(GroupCategoryMapper.class);

    GroupCategory groupCategoryDtoToGroupCategory(GroupCategoryDto groupCategoryDto);

    GroupCategoryDto groupCategoryToGroupCategoryDto(GroupCategory groupCategory);

    @Mapping(target = "id", ignore = true)
    void updateGroupCategoryFromDto(GroupCategoryDto groupCategoryDto, @MappingTarget GroupCategory groupCategory);
}