package ru.kliuevia.springapp.mapper;

import org.mapstruct.Mapper;
import ru.kliuevia.springapp.entity.Role;
import ru.kliuevia.springapp.entity.dto.RoleDto;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    Role toEntity(RoleDto dto);

    RoleDto toDto(Role dto);
}
