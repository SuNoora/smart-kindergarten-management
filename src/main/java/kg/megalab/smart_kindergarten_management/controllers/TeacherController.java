package kg.megalab.smart_kindergarten_management.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kg.megalab.smart_kindergarten_management.models.dto.TeacherDto;
import kg.megalab.smart_kindergarten_management.services.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teacher")
@Tag(name = "Teacher Controller", description = "Методы управления учителями")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping
    @Operation(summary = "Создание учителя", description = "Создает нового учителя в системе")
    @ApiResponse(responseCode = "201", description = "Учитель успешно создан")
    @ApiResponse(responseCode = "400", description = "Ошибка валидации")
    @ApiResponse(responseCode = "409", description = "Учитель уже существует")
    public ResponseEntity<TeacherDto> createTeacher(
            @Valid @RequestBody TeacherDto teacherDto) {
        TeacherDto createdTeacher = teacherService.createTeacher(teacherDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTeacher);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление учителя", description = "Обновляет информацию об учителе")
    @ApiResponse(responseCode = "200", description = "Учитель успешно обновлен")
    @ApiResponse(responseCode = "400", description = "Ошибка валидации")
    @ApiResponse(responseCode = "404", description = "Учитель не найден")

    public ResponseEntity<TeacherDto> updateTeacher(
            @Parameter(description = "ID учителя") @PathVariable Long id,
            @Valid @RequestBody TeacherDto teacherDto) {
        TeacherDto updatedTeacher = teacherService.updateTeacher(id, teacherDto);
        return ResponseEntity.ok(updatedTeacher);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление учителя", description = "Удаляет учителя из системы")
    @ApiResponse(responseCode = "200", description = "Учитель успешно удален")
    @ApiResponse(responseCode = "404", description = "Учитель не найден")
    @ApiResponse(responseCode = "409", description = "Нельзя удалить учителя, назначенного на группу")
    public ResponseEntity<Void> deleteTeacher(
            @Parameter(description = "ID учителя") @PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение учителя по ID", description = "Возвращает информацию об учителе")
    @ApiResponse(responseCode = "200", description = "Учитель найден")
    @ApiResponse(responseCode = "404", description = "Учитель не найден")
    public ResponseEntity<TeacherDto> getTeacherById(
            @Parameter(description = "ID учителя") @PathVariable Long id) {
        TeacherDto teacher = teacherService.findTeacherById(id);
        return ResponseEntity.ok(teacher);
    }

    @GetMapping
    @Operation(summary = "Получение списка учителей",
            description = "Возвращает список учителей с пагинацией, отсортированный по степени и алфавиту")
    @ApiResponse(responseCode = "200", description = "Список учителей найден")
    @ApiResponse(responseCode = "400", description = "Неверные параметры пагинации")
    public ResponseEntity<Page<TeacherDto>> getAllTeachers(
            @Parameter(description = "Номер страницы (начиная с 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Размер страницы") @RequestParam(defaultValue = "10") int size) {
        Page<TeacherDto> teachers = teacherService.findAllTeachers(page, size);
        return ResponseEntity.ok(teachers);
    }
}