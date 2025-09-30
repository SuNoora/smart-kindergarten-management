package kg.megalab.smart_kindergarten_management.mappers;

import kg.megalab.smart_kindergarten_management.models.Child;
import kg.megalab.smart_kindergarten_management.models.Enrollment;
import kg.megalab.smart_kindergarten_management.models.Group;
import kg.megalab.smart_kindergarten_management.models.dto.EnrollChildDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {

    EnrollmentMapper INSTANCE = Mappers.getMapper(EnrollmentMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "child", ignore = true)
    @Mapping(target = "group", ignore = true)
    @Mapping(target = "payments", ignore = true)
    @Mapping(target = "startDate", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "endDate", ignore = true)
    @Mapping(target = "price", source = "price")
    Enrollment enrollChildDtoToEnrollment(EnrollChildDto enrollChildDto);
}