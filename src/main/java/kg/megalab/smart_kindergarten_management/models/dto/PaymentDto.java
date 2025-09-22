package kg.megalab.smart_kindergarten_management.models.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentDto {

    @Positive
    Long id;

    @NotNull(message = "ID записи ребенка в группе обязателен")
    Long groupChildrenId;

    @NotNull(message = "Сумма платежа обязательна")
    @Positive(message = "Сумма должна быть положительной")
    Double amount;
    @NotNull(message = "Дата платежа обязательна")
    LocalDate paymentDate;
}
