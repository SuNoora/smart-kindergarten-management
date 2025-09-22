package kg.megalab.smart_kindergarten_management.models.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PreviousMonthDto {

    Long childId;
    Double amountDue; // сумма к оплате за прошлый месяц
}
