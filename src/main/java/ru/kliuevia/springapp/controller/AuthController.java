package ru.kliuevia.springapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import ru.kliuevia.springapp.entity.dto.ErrorResponse;
import ru.kliuevia.springapp.entity.dto.request.JwtAccessRequestDto;
import ru.kliuevia.springapp.entity.dto.request.JwtRefreshRequestDto;
import ru.kliuevia.springapp.entity.dto.response.JwtResponseDto;

@Tag(name = "Аутентификация", description = "Операции для получения access/refresh JWT токенов")
public interface AuthController {

    @Operation(summary = "Получить JWT токены", description = "По логину и паролю получить пару access/refresh jwt токенов")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешная аутентификация",
                    content = @Content(
                            schema = @Schema(implementation = JwtResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Требуется аутентификация",
                    content = @Content(schema = @Schema())
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Не хватает прав доступа",
                    content = @Content(schema = @Schema())
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Ошибка валидации запроса",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = @Content(schema = @Schema())
            )
    })
    ResponseEntity<JwtResponseDto> accessToken(JwtAccessRequestDto jwtAccessRequestDto);

    @Operation(summary = "Обновить JWT токены", description = "По refresh токену получить новую пару access/refresh jwt токенов")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное обнвление токенов",
                    content = @Content(
                            schema = @Schema(implementation = JwtResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Refresh jwt токен просрочился/поврежден",
                    content = @Content(schema = @Schema())
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Не хватает прав доступа",
                    content = @Content(schema = @Schema())
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Ошибка валидации запроса",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = @Content(schema = @Schema())
            )
    })
    ResponseEntity<JwtResponseDto> refreshToken(JwtRefreshRequestDto jwtRefreshRequestDto);
}
