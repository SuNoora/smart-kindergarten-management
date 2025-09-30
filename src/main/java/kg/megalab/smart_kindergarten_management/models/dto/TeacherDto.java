package kg.megalab.smart_kindergarten_management.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kg.megalab.smart_kindergarten_management.models.enums.TeacherDegree;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDto {

    Long id;

    @NotBlank(message = "Имя обязательно")
    String firstName;

    @NotBlank(message = "Фамилия обязательна")
    String lastName;

    String patronymic;

    @NotNull(message = "Степень/роль обязательна")
    TeacherDegree teacherDegree;

    @NotNull(message = "Статус обязателен")
    Boolean active;

}
