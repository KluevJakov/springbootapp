package ru.kliuevia.springapp.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import ru.kliuevia.springapp.BaseIntegrationTest;
import ru.kliuevia.springapp.entity.Role;
import ru.kliuevia.springapp.entity.User;
import ru.kliuevia.springapp.entity.dto.RoleDto;
import ru.kliuevia.springapp.entity.dto.request.UserCreateRequestDto;
import ru.kliuevia.springapp.repository.RoleRepository;
import ru.kliuevia.springapp.repository.UserRepository;

import java.util.UUID;

public class UserServiceTest extends BaseIntegrationTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @BeforeEach
    public void clearUserTable() {
        userRepository.deleteAll();
    }

    @Test
    public void when_saveUserWithExistedLogin_then_throwIllegalArgumentException() {
        String existedLogin = "userLogin";
        userRepository.save(User.builder()
                .login(existedLogin)
                .build());
        UserCreateRequestDto input = UserCreateRequestDto.builder()
                .login(existedLogin)
                .password("password123")
                .build();

        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.save(input));
    }

//    @Test
//    public void when_deleteRoleByUuid_then_executeSuccessfully() {
//        UUID expectedUuid = roleRepository.save(Role.builder()
//                        .name("МОДЕРАТОР")
//                        .build())
//                .getId();
//        Assertions.assertEquals(1, roleRepository.findAll().size());
//
//        roleService.delete(expectedUuid);
//
//        Assertions.assertEquals(0, roleRepository.findAll().size());
//    }
}
