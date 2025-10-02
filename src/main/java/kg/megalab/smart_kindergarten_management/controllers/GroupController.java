package kg.megalab.smart_kindergarten_management.controllers;

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
@RequiredArgsConstructor
public class GroupController{

    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<GroupDto> createGroup(
            @Valid @RequestBody GroupDto groupDto) {
        GroupDto createdGroup = groupService.createGroup(groupDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGroup);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupDto> updateGroup(
            @PathVariable Long id,
            @Valid @RequestBody GroupDto groupDto) {
        GroupDto updatedGroup = groupService.updateGroup(id, groupDto);
        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(
            @PathVariable Long id) {
        groupService.deleteGroup(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDto> getGroupById(
            @PathVariable Long id) {
        GroupDto group = groupService.findGroupById(id);
        return ResponseEntity.ok(group);
    }

    @GetMapping

    public ResponseEntity<Page<GroupDto>> getAllGroups(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<GroupDto> groups = groupService.findAllGroups(page, size);
        return ResponseEntity.ok(groups);
    }

}