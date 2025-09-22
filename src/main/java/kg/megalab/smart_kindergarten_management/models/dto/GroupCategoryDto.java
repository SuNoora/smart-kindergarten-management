package kg.megalab.smart_kindergarten_management.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupCategoryDto {
    @Positive
    Long id;

    @NotBlank(message = "Название категории обязательно")
    String name;
    @NotNull(message = "Статус активности обязателен")
    Boolean active;

    @NotNull(message = "Цена обязательна")
    @Positive(message = "Цена должна быть положительной")
    Integer price;
}
