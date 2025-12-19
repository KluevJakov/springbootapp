package ru.kliuevia.springapp.controller.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kliuevia.springapp.controller.AuthController;
import ru.kliuevia.springapp.entity.dto.request.JwtAccessRequestDto;
import ru.kliuevia.springapp.entity.dto.request.JwtRefreshRequestDto;
import ru.kliuevia.springapp.entity.dto.response.JwtResponseDto;
import ru.kliuevia.springapp.service.AuthService;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    @PostMapping("/accessToken")
    public ResponseEntity<JwtResponseDto> accessToken(@RequestBody @Valid JwtAccessRequestDto jwtAccessRequestDto) {
        return ResponseEntity.ok(authService.accessToken(jwtAccessRequestDto));
    }

    @Override
    @PostMapping("/refreshToken")
    public ResponseEntity<JwtResponseDto> refreshToken(@RequestBody @Valid JwtRefreshRequestDto jwtRefreshRequestDto) {
        return ResponseEntity.ok(authService.refreshToken(jwtRefreshRequestDto));
    }

}
