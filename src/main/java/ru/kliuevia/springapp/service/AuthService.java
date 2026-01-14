package ru.kliuevia.springapp.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.kliuevia.springapp.entity.User;
import ru.kliuevia.springapp.entity.dto.request.JwtAccessRequestDto;
import ru.kliuevia.springapp.entity.dto.request.JwtRefreshRequestDto;
import ru.kliuevia.springapp.entity.dto.response.JwtResponseDto;
import ru.kliuevia.springapp.entity.enums.TokenType;
import ru.kliuevia.springapp.mapper.JwtMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final StringRedisTemplate stringRedisTemplate;
    private final JwtMapper jwtMapper;

    public JwtResponseDto accessToken (JwtAccessRequestDto jwtAccessRequestDto) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(jwtAccessRequestDto.getLogin(), jwtAccessRequestDto.getPassword());

        try {
            token = (UsernamePasswordAuthenticationToken) authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(token);
            User authenticatedUser = (User) token.getPrincipal();


            log.info("Пользователь {} успешно вошел в систему", jwtAccessRequestDto.getLogin());
            return jwtMapper.toJwtResponseDto(jwtService.pairTokens(jwtAccessRequestDto.getLogin(), authenticatedUser));
        } catch (DisabledException ex) {
            log.error("Учетная запись не подтверждена: {}", ex.getMessage());
            throw new DisabledException("Учетная запись не подтверждена");
        } catch (BadCredentialsException ex) {
            log.error("Неверные учетные данные: {}", ex.getMessage());
            throw new BadCredentialsException("Неверные учетные данные");
        } catch (AuthenticationException ex) {
            log.error("Ошибка аутентификации: {}", ex.getMessage());
            throw new RuntimeException("Ошибка аутентификации");
        }
    }


    public JwtResponseDto refreshToken (JwtRefreshRequestDto jwtRefreshRequestDto) {
        Claims claims = jwtService.getClaims(TokenType.REFRESH, jwtRefreshRequestDto.getRefreshToken());
        String login = claims.getSubject();
        return jwtMapper.toJwtResponseDto(jwtService.pairTokens(login, null));
    }
}
