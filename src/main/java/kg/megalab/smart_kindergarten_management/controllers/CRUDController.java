package kg.megalab.smart_kindergarten_management.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface CRUDController<CreateDto, Dto> {

    @PostMapping
    ResponseEntity<Dto> create(@RequestBody CreateDto createDto);

    @PutMapping("/{id}")
    ResponseEntity<Dto> update(@RequestBody Dto dto, @PathVariable Long id);

    @GetMapping("/{id}")
    ResponseEntity<Dto> findById(@PathVariable Long id);

    @GetMapping("/all")
    ResponseEntity<?> findAll(@RequestParam int pageNo, @RequestParam int pageSize);

    @DeleteMapping("/{id}")
    ResponseEntity<Dto> deleteById(@PathVariable Long id);
}
