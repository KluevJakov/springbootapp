package ru.kliuevia.springapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import ru.kliuevia.springapp.entity.dto.ErrorResponse;
import ru.kliuevia.springapp.entity.dto.response.UserResponseDto;

import java.util.UUID;

@Tag(name = "Пользователи", description = "Операции над пользователями приложения")
public interface UserController {

    @Operation(summary = "Активировать учетную запись пользователя",
            description = "По полученному SMS пользователь активирует свой аккаунт для входа")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешная активация",
                    content = @Content(
                            schema = @Schema(implementation = UserResponseDto.class)
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
    ResponseEntity<UserResponseDto> activate(
            @Parameter(description = "Код активации, направленный по SMS", example = "9e5e6264-e55f-427e-9e62-64e55f127ee3")
            UUID activationCode);
}
