package kg.megalab.smart_kindergarten_management.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.megalab.smart_kindergarten_management.models.dto.GroupCategoryDto;
import kg.megalab.smart_kindergarten_management.services.GroupCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group-category")
@Tag(name = "Group Category Controller", description = "Методы управления категориями групп")
@RequiredArgsConstructor
public class GroupCategoryController {

    private final GroupCategoryService groupCategoryService;

    @PostMapping
    @Operation(summary = "Создание категории группы")
    public ResponseEntity<GroupCategoryDto> create(@RequestBody GroupCategoryDto groupCategoryDto) {
        GroupCategoryDto created = groupCategoryService.createGroupCategory(groupCategoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление категории группы")
    public ResponseEntity<GroupCategoryDto> update(@RequestBody GroupCategoryDto dto, @PathVariable Long id) {
        return ResponseEntity.ok(groupCategoryService.updateGroupCategory(dto, id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Поиск категории группы по ID")
    public ResponseEntity<GroupCategoryDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(groupCategoryService.findGroupCategoryById(id));
    }

    @GetMapping("/all")
    @Operation(summary = "Получение всех категорий групп с пагинацией")
    public ResponseEntity<?> findAll(@RequestParam int pageNo, @RequestParam int pageSize) {
        return ResponseEntity.ok(groupCategoryService.findAllGroupCategory(pageNo, pageSize));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление категории группы")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        groupCategoryService.deleteGroupCategory(id);
        return ResponseEntity.noContent().build();
    }
}