package kg.megalab.smart_kindergarten_management.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/payments")
@Tag(name = "Payment Controller", description = "Методы управления платежами")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @Operation(summary = "Добавление платежа",
            description = "Добавление новой записи о платеже за ребенка")
    @ApiResponse(responseCode = "201", description = "Платеж успешно добавлен")
    @ApiResponse(responseCode = "400", description = "Ошибка валидации DTO")
    @ApiResponse(responseCode = "404", description = "Запись о ребенке в группе не найдена")
    @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    public ResponseEntity<Payment> createPayment(@Valid @RequestBody PaymentDto paymentDto) {
        Payment payment = paymentService.createPayment(paymentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    }

    @GetMapping("/previous-month/{childId}")
    @Operation(summary = "Получение задолженности за прошлый месяц",
            description = "Возвращает сумму задолженности ребенка за прошлый календарный месяц")
    @ApiResponse(responseCode = "200", description = "Возвращает сумму задолженности")
    @ApiResponse(responseCode = "404", description = "Ребенок не найден или нет активной группы")
    @ApiResponse(responseCode = "400", description = "Неверный формат ID")
    @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    public ResponseEntity<PreviousMonthDto> getPreviousMonthDebt(
            @Parameter(description = "ID ребенка") @PathVariable Long childId) {
        PreviousMonthDto debt = paymentService.getPreviousMonth(childId);
        return ResponseEntity.ok(debt);
    }
}