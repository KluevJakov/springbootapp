package ru.kliuevia.springapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.kliuevia.springapp.entity.Role;
import ru.kliuevia.springapp.entity.User;
import ru.kliuevia.springapp.entity.dto.request.UserCreateRequestDto;
import ru.kliuevia.springapp.entity.dto.request.UserUpdateRequestDto;
import ru.kliuevia.springapp.entity.dto.response.UserResponseDto;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper {

    @Mapping(target = "enable", ignore = true)
    @Mapping(target = "activationCode", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    User createToEntity(UserCreateRequestDto dto);

    default User updateToEntity(User oldData, UserUpdateRequestDto newData) {
        oldData.setLogin(newData.getLogin());
        oldData.setGroupNumber(newData.getGroupNumber());
        oldData.setRole(Role.builder()
                        .id(newData.getRole().getId())
                .build());

        return oldData;
    }

    UserResponseDto toDto(User user);

}
