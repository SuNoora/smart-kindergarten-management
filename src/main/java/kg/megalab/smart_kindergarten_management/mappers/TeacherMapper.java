package kg.megalab.smart_kindergarten_management.mappers;

import kg.megalab.smart_kindergarten_management.models.Teacher;
import kg.megalab.smart_kindergarten_management.models.dto.TeacherDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);

    Teacher teacherDtoToTeacher(TeacherDto teacherDto);

    TeacherDto teacherToTeacherDto(Teacher teacher);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "groups", ignore = true)
    void updateTeacherFromDto(TeacherDto teacherDto, @MappingTarget Teacher teacher);
}