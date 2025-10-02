package kg.megalab.smart_kindergarten_management.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kg.megalab.smart_kindergarten_management.models.Enrollment;
import kg.megalab.smart_kindergarten_management.models.dto.EnrollChildDto;
import kg.megalab.smart_kindergarten_management.models.dto.WithdrawChildDto;
import kg.megalab.smart_kindergarten_management.services.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group-children")
@Tag(name = "Enrollment Controller", description = "Методы зачисления и отчисления детей")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    @Operation(summary = "Зачисление ребенка в группу",
            description = "Зачисление нового ребенка в садик и запись его в выбранную группу")
    @ApiResponse(responseCode = "201", description = "Ребенок успешно зачислен")
    @ApiResponse(responseCode = "400", description = "Ошибка валидации DTO")
    @ApiResponse(responseCode = "404", description = "Группа не найдена")
    @ApiResponse(responseCode = "409", description = "Группа заполнена")
    @ApiResponse(responseCode = "500", description = "Ошибка сервера")

    public ResponseEntity<Enrollment> enrollChild(@Valid @RequestBody EnrollChildDto enrollChildDto) {
        Enrollment enrollment = enrollmentService.enrollChild(enrollChildDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(enrollment);
    }

    @PutMapping("/{id}/withdraw")
    @Operation(summary = "Отчисление ребенка из группы",
            description = "Отчисление ребенка из группы с проставлением даты окончания")
    @ApiResponse(responseCode = "200", description = "Ребенок успешно отчислен")
    @ApiResponse(responseCode = "400", description = "Неверные данные")
    @ApiResponse(responseCode = "404", description = "Запись ребенка в группе не найдена")
    @ApiResponse(responseCode = "500", description = "Ошибка сервера")

    public ResponseEntity<Enrollment> withdrawChild(
            @Parameter(description = "ID записи ребенка в группе") @PathVariable Long id,
            @RequestBody(required = false) WithdrawChildDto withdrawChildDto) {

        if (withdrawChildDto == null) {
            withdrawChildDto = new WithdrawChildDto();
        }

        Enrollment enrollment = enrollmentService.withdrawChild(id, withdrawChildDto);
        return ResponseEntity.ok(enrollment);
    }
}