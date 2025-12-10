package ru.kliuevia.springapp.entity.dto.response;

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
public class UserResponseDto {
    private UUID id;
    private String login;
    private Integer groupNumber;
    private RoleDto role;
}
