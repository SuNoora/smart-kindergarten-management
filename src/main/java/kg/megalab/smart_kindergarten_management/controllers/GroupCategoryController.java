package kg.megalab.smart_kindergarten_management.controllers;

import kg.megalab.smart_kindergarten_management.models.dto.GroupCategoryDto;
import kg.megalab.smart_kindergarten_management.services.GroupCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group-category")
@RequiredArgsConstructor
public class GroupCategoryController {

    private final GroupCategoryService groupCategoryService;

    @PostMapping
    public ResponseEntity<GroupCategoryDto> create(@RequestBody GroupCategoryDto groupCategoryDto) {
        GroupCategoryDto created = groupCategoryService.createGroupCategory(groupCategoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupCategoryDto> update(@RequestBody GroupCategoryDto dto, @PathVariable Long id) {
        return ResponseEntity.ok(groupCategoryService.updateGroupCategory(dto, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupCategoryDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(groupCategoryService.findGroupCategoryById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll(@RequestParam int pageNo, @RequestParam int pageSize) {
        return ResponseEntity.ok(groupCategoryService.findAllGroupCategory(pageNo, pageSize));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        groupCategoryService.deleteGroupCategory(id);
        return ResponseEntity.noContent().build();
    }
}