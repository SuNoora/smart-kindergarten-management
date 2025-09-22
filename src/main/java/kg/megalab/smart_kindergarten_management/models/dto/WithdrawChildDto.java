package kg.megalab.smart_kindergarten_management.models.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WithdrawChildDto {
    // Если null, ставится сегодняшняя дата
    LocalDate endDate;
}
