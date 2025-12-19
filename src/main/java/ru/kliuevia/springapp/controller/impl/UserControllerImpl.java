package ru.kliuevia.springapp.controller.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kliuevia.springapp.controller.UserController;
import ru.kliuevia.springapp.entity.dto.request.UserCreateRequestDto;
import ru.kliuevia.springapp.entity.dto.request.UserUpdateRequestDto;
import ru.kliuevia.springapp.entity.dto.response.UserResponseDto;
import ru.kliuevia.springapp.service.UserService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @GetMapping("/{uuid}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable("uuid") UUID userId) {
        return ResponseEntity.ok(userService.getByUuid(userId));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> save(@RequestBody @Valid UserCreateRequestDto user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @Override
    @PostMapping("/activate/{uuid}")
    public ResponseEntity<UserResponseDto> activate(@PathVariable("uuid") UUID activationCode) {
        userService.activate(activationCode);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<UserResponseDto> update(@RequestBody @Valid UserUpdateRequestDto user) {
        return ResponseEntity.ok(userService.edit(user));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteById(@PathVariable("uuid") UUID userId) {
        userService.delete(userId);
        return ResponseEntity.ok().build();
    }
}
