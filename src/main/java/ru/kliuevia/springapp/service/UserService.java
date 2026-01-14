package ru.kliuevia.springapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.kliuevia.springapp.config.Constants;
import ru.kliuevia.springapp.entity.Role;
import ru.kliuevia.springapp.entity.User;
import ru.kliuevia.springapp.entity.dto.request.SendSmsRequestDto;
import ru.kliuevia.springapp.entity.dto.request.UserCreateRequestDto;
import ru.kliuevia.springapp.entity.dto.request.UserUpdateRequestDto;
import ru.kliuevia.springapp.entity.dto.response.UserResponseDto;
import ru.kliuevia.springapp.exceptions.NotFoundException;
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
    private final KafkaService kafkaService;
    private final ObjectMapper objectMapper;

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

//        try {
//            ResponseEntity<StatusDto> response = restTemplate.postForEntity(SMS_API_URL,
//                    SendSmsRequestDto.builder()
//                            .destination(user.getLogin())
//                            .number(ALFA_NAME)
//                            .text(String.format(Constants.Sms.TEXT, user.getActivationCode()))
//                            .build(),
//                    StatusDto.class);
//            if (response.getStatusCode().is2xxSuccessful()) {
//                log.info("SMS активация успешно отправлена");
//            } else {
//                log.error("Ошибка интеграции с SMS API");
//            }
//        } catch (Exception ignored) {}

        try {
            String value = objectMapper.writeValueAsString(SendSmsRequestDto.builder()
                    .destination(user.getLogin())
                    .number(ALFA_NAME)
                    .text(String.format(Constants.Sms.TEXT, user.getActivationCode()))
                    .build());

            kafkaService.publish(value);
        } catch (JsonProcessingException e) {
            log.error("Произошла ошибка при сериализации для отправки запроса в mocksmsapi, {}", e.getMessage());
        } catch (Exception e) {
            log.error("Произошла ошибка при отправке сообщения в кафку {}", e.getMessage());
        }

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @CacheEvict(cacheNames = "users", key = "#uuid")
    public void delete(UUID uuid) {
        userRepository.deleteById(uuid);
    }


    @CachePut(cacheNames = "users", key = "#result.id")
    public UserResponseDto edit(UserUpdateRequestDto newData) {
        User oldUser = userRepository.findById(newData.getId())
                .orElseThrow(() -> new IllegalArgumentException("Пользователя с таким логином не существует"));

        var entityToUpdate = userMapper.updateToEntity(oldUser, newData);
        entityToUpdate.setPassword(passwordEncoder.encode(newData.getPassword()));

        User savedUser = userRepository.save(entityToUpdate);
        return userMapper.toDto(savedUser);
    }

    @Cacheable(cacheNames = "users", key = "#uuid")
    public UserResponseDto getByUuid(UUID uuid) {
        return userRepository.findById(uuid)
                .map(userMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден по id: " + uuid));
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
