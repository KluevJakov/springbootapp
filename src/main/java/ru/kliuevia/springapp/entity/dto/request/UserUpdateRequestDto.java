package ru.kliuevia.springapp.entity.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kliuevia.springapp.entity.dto.RoleDto;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateRequestDto {
    @NotBlank(message = "При редактировании поле 'id' обязательное")
    private UUID id;
    @NotBlank(message = "Логин не может быть пустым")
    private String login;
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
    private Integer groupNumber;
    private RoleDto role;
}
