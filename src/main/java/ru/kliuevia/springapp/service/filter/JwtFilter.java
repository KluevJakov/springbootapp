package ru.kliuevia.springapp.service.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.kliuevia.springapp.entity.enums.TokenType;
import ru.kliuevia.springapp.entity.security.JwtAuthentication;
import ru.kliuevia.springapp.service.JwtService;

import java.io.IOException;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private static final String BEARER = "Bearer ";

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) {
        try {
            String jwt = parseJwt(request);

            if (jwt == null) {
                filterChain.doFilter(request, response);
                return;
            }

            Claims claims = jwtService.getClaims(TokenType.ACCESS, jwt);
            JwtAuthentication authentication = jwtService.authentication(claims);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            log.error("Ошибка в Jwt фильтре {}", exception.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private String parseJwt(HttpServletRequest request) {
        String headerData = request.getHeader("Authorization");

        if (StringUtils.isNotBlank(headerData) && headerData.startsWith(BEARER)) {
            return headerData.substring(BEARER.length());
        }

        return null;
    }
}
