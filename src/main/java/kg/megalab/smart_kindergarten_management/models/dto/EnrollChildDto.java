package kg.megalab.smart_kindergarten_management.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EnrollChildDto {

    @NotBlank(message = "Имя обязательно")
    String firstName;
    @NotBlank(message = "Фамилия обязательна")
    String lastName;
    String patronymic;
    @NotNull(message = "Дата рождения обязательна")
    LocalDate dateOfBirth;

    @NotNull(message = "ID группы обязателен")
    Long groupId;

    Double price; // если индивидуальная цена
}
