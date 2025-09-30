package kg.megalab.smart_kindergarten_management.services;

import kg.megalab.smart_kindergarten_management.models.dto.PaymentDto;
import kg.megalab.smart_kindergarten_management.models.Payment;
import kg.megalab.smart_kindergarten_management.models.dto.PreviousMonthDto;

public interface PaymentService {

    Payment createPayment(PaymentDto paymentDto);

    PreviousMonthDto getPreviousMonth(Long childId);
}