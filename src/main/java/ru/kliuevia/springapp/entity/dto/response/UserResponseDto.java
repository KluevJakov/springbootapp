package ru.kliuevia.springapp.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kliuevia.springapp.entity.dto.RoleDto;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto implements Serializable {
    private UUID id;
    private String login;
    private String description;
    private Integer groupNumber;
    private RoleDto role;
}
