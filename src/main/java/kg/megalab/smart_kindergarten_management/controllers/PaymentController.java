package kg.megalab.smart_kindergarten_management.controllers;

import jakarta.validation.Valid;
import kg.megalab.smart_kindergarten_management.models.Payment;
import kg.megalab.smart_kindergarten_management.models.dto.PaymentDto;
import kg.megalab.smart_kindergarten_management.models.dto.PreviousMonthDto;
import kg.megalab.smart_kindergarten_management.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Payment> createPayment(@Valid @RequestBody PaymentDto paymentDto) {
        Payment payment = paymentService.createPayment(paymentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    }

    @GetMapping("/previous-month/{childId}")
    public ResponseEntity<PreviousMonthDto> getPreviousMonthDebt(
            @PathVariable Long childId) {
        PreviousMonthDto debt = paymentService.getPreviousMonth(childId);
        return ResponseEntity.ok(debt);
    }
}