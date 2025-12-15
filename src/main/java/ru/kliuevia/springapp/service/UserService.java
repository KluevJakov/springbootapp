package ru.kliuevia.springapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.kliuevia.springapp.config.Constants;
import ru.kliuevia.springapp.entity.Role;
import ru.kliuevia.springapp.entity.User;
import ru.kliuevia.springapp.entity.dto.StatusDto;
import ru.kliuevia.springapp.entity.dto.request.SendSmsRequestDto;
import ru.kliuevia.springapp.entity.dto.request.UserCreateRequestDto;
import ru.kliuevia.springapp.entity.dto.request.UserUpdateRequestDto;
import ru.kliuevia.springapp.entity.dto.response.UserResponseDto;
import ru.kliuevia.springapp.mapper.UserMapper;
import ru.kliuevia.springapp.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${smsapi.url}")
    private String SMS_API_URL;
    @Value("${smsapi.alfa-name}")
    private String ALFA_NAME;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RestTemplate restTemplate;

    public UserResponseDto save(UserCreateRequestDto userDto) {
        boolean userExists = userRepository.existsByLogin(userDto.getLogin());

        if (userExists) {
            throw new IllegalArgumentException("Логин занят");
        }

        User user = userMapper.createToEntity(userDto);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.builder()
                .id(Constants.Roles.USER_ID)
                .build());
        user.setActivationCode(UUID.randomUUID());

        try {
            ResponseEntity<StatusDto> response = restTemplate.postForEntity(SMS_API_URL,
                    SendSmsRequestDto.builder()
                            .destination(user.getLogin())
                            .number(ALFA_NAME)
                            .text(String.format(Constants.Sms.TEXT, user.getActivationCode()))
                            .build(),
                    StatusDto.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("SMS активация успешно отправлена");
            } else {
                log.error("Ошибка интеграции с SMS API");
            }
        } catch (Exception ignored) {}

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    public void delete(UUID uuid) {
        userRepository.deleteById(uuid);
    }

    public UserResponseDto edit(UserUpdateRequestDto newData) {
        User oldUser = userRepository.findById(newData.getId())
                .orElseThrow(() -> new IllegalArgumentException("Пользователя с таким логином не существует"));

        var entityToUpdate = userMapper.updateToEntity(oldUser, newData);
        entityToUpdate.setPassword(passwordEncoder.encode(newData.getPassword()));

        User savedUser = userRepository.save(entityToUpdate);
        return userMapper.toDto(savedUser);
    }

    public UserResponseDto getByUuid(UUID uuid) {
        return userRepository.findById(uuid)
                .map(userMapper::toDto)
                .orElseThrow(IllegalArgumentException::new);
    }

    public List<UserResponseDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    public void activate(UUID activationCode) {
        User user = userRepository.findByActivationCode(activationCode)
                .orElseThrow(() -> new IllegalArgumentException("Код активации некорректный"));

        user.setActivationCode(null);
        user.setEnable(true);

        userRepository.save(user);
    }
}
