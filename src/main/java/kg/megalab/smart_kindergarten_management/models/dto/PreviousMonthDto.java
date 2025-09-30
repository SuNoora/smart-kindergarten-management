package kg.megalab.smart_kindergarten_management.models.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class PreviousMonthDto {

    Long childId;
    Integer amountDue; // сумма к оплате за прошлый месяц
}
