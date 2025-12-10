package ru.kliuevia.springapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kliuevia.springapp.entity.dto.RoleDto;
import ru.kliuevia.springapp.service.RoleService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/{uuid}")
    public ResponseEntity<RoleDto> findById(@PathVariable("uuid") UUID roleId) {
        return ResponseEntity.ok(roleService.getByUuid(roleId));
    }

    @GetMapping
    public ResponseEntity<List<RoleDto>> findAll() {
        return ResponseEntity.ok(roleService.getAll());
    }

    @PostMapping
    public ResponseEntity<RoleDto> save(@RequestBody @Valid RoleDto role) {
        return ResponseEntity.ok(roleService.save(role));
    }

    @PutMapping
    public ResponseEntity<RoleDto> update(@RequestBody @Valid RoleDto role) {
        return ResponseEntity.ok(roleService.edit(role));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteById(@PathVariable("uuid") UUID roleId) {
        roleService.delete(roleId);
        return ResponseEntity.ok().build();
    }
}
