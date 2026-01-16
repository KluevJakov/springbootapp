package ru.kliuevia.springapp.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.kliuevia.springapp.BaseIntegrationTest;
import ru.kliuevia.springapp.entity.Role;
import ru.kliuevia.springapp.entity.dto.RoleDto;
import ru.kliuevia.springapp.repository.RoleRepository;

import java.util.UUID;

public class RoleServiceTest extends BaseIntegrationTest {

    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    public void clearRoleTable() {
        roleRepository.deleteAll();
    }

    @Test
    public void when_saveCorrectDto_then_executeSuccessfully() {
        RoleDto expected = RoleDto.builder()
                .name("ТЕСТ")
                .build();

        RoleDto actual = roleService.save(expected);

        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertNotNull(actual.getId());
    }

    @Test
    public void when_deleteRoleByUuid_then_executeSuccessfully() {
        UUID expectedUuid = roleRepository.save(Role.builder()
                        .name("МОДЕРАТОР")
                        .build())
                .getId();
        Assertions.assertEquals(1, roleRepository.findAll().size());

        roleService.delete(expectedUuid);

        Assertions.assertEquals(0, roleRepository.findAll().size());
    }
}
