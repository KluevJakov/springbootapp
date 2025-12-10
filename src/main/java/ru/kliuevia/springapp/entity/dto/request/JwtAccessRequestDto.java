package ru.kliuevia.springapp.entity.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtAccessRequestDto {
    @NotBlank(message = "Введите логин")
    private String login;
    @NotBlank(message = "Введите пароль")
    private String password;
}
