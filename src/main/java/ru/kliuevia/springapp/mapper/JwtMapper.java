package ru.kliuevia.springapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.kliuevia.springapp.entity.Tuple;
import ru.kliuevia.springapp.entity.dto.response.JwtResponseDto;

@Mapper(componentModel = "spring")
public interface JwtMapper {

    @Mapping(target = "accessToken", source = "tuple.t1")
    @Mapping(target = "refreshToken", source = "tuple.t2")
    JwtResponseDto toJwtResponseDto(Tuple<String, String> tuple);
}
