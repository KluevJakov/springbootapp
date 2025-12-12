package ru.kliuevia.springapp.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.kliuevia.springapp.entity.Role;
import ru.kliuevia.springapp.entity.Tuple;
import ru.kliuevia.springapp.entity.User;
import ru.kliuevia.springapp.entity.enums.TokenType;
import ru.kliuevia.springapp.entity.security.JwtAuthentication;
import ru.kliuevia.springapp.repository.UserRepository;
import ru.kliuevia.springapp.utils.DateUtils;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import static ru.kliuevia.springapp.entity.enums.TokenType.ACCESS;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    public static final String USER_ID = "userId";
    public static final String ROLE = "role";

    @Value("${jwt.access.expiration}")
    private String accessExpirationTime;
    @Value("${jwt.access.seed}")
    private String accessSecretSeed;

    @Value("${jwt.refresh.expiration}")
    private String refreshExpirationTime;
    @Value("${jwt.refresh.seed}")
    private String refreshSecretSeed;

    private final UserRepository userRepository;

    public Tuple<String, String> pairTokens(String login, User user) {

        if (user == null) {
            user = userRepository.findByLogin(login)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Пользователь с логином %s не найден", login)));
        }

        Date currentDate = DateUtils.now();
        return Tuple.of(buildAccessToken(login, currentDate, user), buildRefreshToken(login, currentDate));
    }

    public String buildAccessToken(String login, Date currentTime, User user) {
        return Jwts.builder()
                .subject(login)
                .issuedAt(currentTime)
                .expiration(DateUtils.plus(currentTime.getTime(), getExpirationTime(TokenType.ACCESS)))
                .claim(USER_ID, user.getId())
                .claim(ROLE, user.getRole().getName())
                .signWith(getSecretKey(TokenType.ACCESS), Jwts.SIG.HS256)
                .compact();
    }

    public String buildRefreshToken(String login, Date currentTime) {
        return Jwts.builder()
                .subject(login)
                .issuedAt(currentTime)
                .expiration(DateUtils.plus(currentTime.getTime(), getExpirationTime(TokenType.REFRESH)))
                .signWith(getSecretKey(TokenType.REFRESH), Jwts.SIG.HS256)
                .compact();
    }


    private SecretKey getSecretKey (TokenType tokenType) {
        byte[] encodedKey = Base64.getDecoder().decode(tokenType == ACCESS ? accessSecretSeed : refreshSecretSeed);
        return Keys.hmacShaKeyFor(encodedKey);
    }

    private Long getExpirationTime (TokenType tokenType) {
        return Long.parseLong(tokenType == ACCESS ? accessExpirationTime : refreshExpirationTime);
    }


    public Claims getClaims(TokenType tokenType, String jwt) {
        return Jwts.parser()
                .verifyWith(getSecretKey(tokenType))
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    public JwtAuthentication authentication(Claims claims) {
        JwtAuthentication authentication = new JwtAuthentication();
        authentication.setRoles(List.of(Role.builder()
                        .name(claims.get(ROLE, String.class))
                .build()));
        authentication.setUsername(claims.getSubject());
        authentication.setUserId(claims.get(USER_ID, String.class));
        authentication.setAuthenticated(true);
        return authentication;
    }
}
