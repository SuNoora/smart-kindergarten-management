package kg.megalab.smart_kindergarten_management.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kg.megalab.smart_kindergarten_management.models.dto.GroupDto;
import kg.megalab.smart_kindergarten_management.services.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group")
@Tag(name = "Group Controller", description = "Методы управления группами")
@RequiredArgsConstructor
public class GroupController{

    private final GroupService groupService;

    @PostMapping
    @Operation(summary = "Создание группы", description = "Создает новую группу в системе")
    @ApiResponse(responseCode = "201", description = "Группа успешно создана")
    @ApiResponse(responseCode = "400", description = "Ошибка валидации")
    @ApiResponse(responseCode = "404", description = "Связанная сущность не найдена")
    @ApiResponse(responseCode = "409", description = "Группа с таким названием уже существует")
    public ResponseEntity<GroupDto> createGroup(
            @Valid @RequestBody GroupDto groupDto) {
        GroupDto createdGroup = groupService.createGroup(groupDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGroup);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление группы", description = "Обновляет информацию о группе")
    @ApiResponse(responseCode = "200", description = "Группа успешно обновлена")
    @ApiResponse(responseCode = "400", description = "Ошибка валидации")
    @ApiResponse(responseCode = "404", description = "Группа или связанные сущности не найдены")
    public ResponseEntity<GroupDto> updateGroup(
            @Parameter(description = "ID группы") @PathVariable Long id,
            @Valid @RequestBody GroupDto groupDto) {
        GroupDto updatedGroup = groupService.updateGroup(id, groupDto);
        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление группы", description = "Удаляет группу из системы")
    @ApiResponse(responseCode = "200", description = "Группа успешно удалена")
    @ApiResponse(responseCode = "404", description = "Группа не найдена")
    @ApiResponse(responseCode = "409", description = "Нельзя удалить группу с зачисленными детьми")
    public ResponseEntity<Void> deleteGroup(
            @Parameter(description = "ID группы") @PathVariable Long id) {
        groupService.deleteGroup(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение группы по ID", description = "Возвращает информацию о группе с расширенными данными")
    @ApiResponse(responseCode = "200", description = "Группа найдена")
    @ApiResponse(responseCode = "404", description = "Группа не найдена")
    public ResponseEntity<GroupDto> getGroupById(
            @Parameter(description = "ID группы") @PathVariable Long id) {
        GroupDto group = groupService.findGroupById(id);
        return ResponseEntity.ok(group);
    }

    @GetMapping
    @Operation(summary = "Получение списка групп",
            description = "Возвращает список групп с пагинацией, отсортированный по категории")
    @ApiResponse(responseCode = "200", description = "Список групп найден")
    @ApiResponse(responseCode = "400", description = "Неверные параметры пагинации")

    public ResponseEntity<Page<GroupDto>> getAllGroups(
            @Parameter(description = "Номер страницы (начиная с 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Размер страницы") @RequestParam(defaultValue = "10") int size) {
        Page<GroupDto> groups = groupService.findAllGroups(page, size);
        return ResponseEntity.ok(groups);
    }

}