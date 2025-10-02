package kg.megalab.smart_kindergarten_management.controllers;

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
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping
    public ResponseEntity<TeacherDto> createTeacher(
            @Valid @RequestBody TeacherDto teacherDto) {
        TeacherDto createdTeacher = teacherService.createTeacher(teacherDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTeacher);
    }

    @PutMapping("/{id}")

    public ResponseEntity<TeacherDto> updateTeacher(
            @PathVariable Long id,
            @Valid @RequestBody TeacherDto teacherDto) {
        TeacherDto updatedTeacher = teacherService.updateTeacher(id, teacherDto);
        return ResponseEntity.ok(updatedTeacher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(
            @PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDto> getTeacherById(
            @PathVariable Long id) {
        TeacherDto teacher = teacherService.findTeacherById(id);
        return ResponseEntity.ok(teacher);
    }

    @GetMapping
    public ResponseEntity<Page<TeacherDto>> getAllTeachers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<TeacherDto> teachers = teacherService.findAllTeachers(page, size);
        return ResponseEntity.ok(teachers);
    }
}