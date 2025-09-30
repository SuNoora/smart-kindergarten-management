package kg.megalab.smart_kindergarten_management.mappers;

import kg.megalab.smart_kindergarten_management.models.Group;
import kg.megalab.smart_kindergarten_management.models.dto.GroupDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);

    // Простое преобразование без сложной логики
    @Mapping(target = "nanny", ignore = true)      // Игнорируем - загрузим в сервисе
    @Mapping(target = "teacher", ignore = true)    // Игнорируем - загрузим в сервисе
    @Mapping(target = "groupCategory", ignore = true) // Игнорируем - загрузим в сервисе
    @Mapping(target = "enrollments", ignore = true)
    Group groupDtoToGroup(GroupDto groupDto);

    // Простое преобразование из Entity в DTO
    @Mapping(target = "nannyId", source = "nanny.id")
    @Mapping(target = "teacherId", source = "teacher.id")
    @Mapping(target = "groupCategoryId", source = "groupCategory.id")
    GroupDto groupToGroupDto(Group group);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "enrollments", ignore = true)
    void updateGroupFromDto(GroupDto groupDto, @MappingTarget Group group);
}