package ru.kliuevia.springapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kliuevia.springapp.entity.dto.RoleDto;
import ru.kliuevia.springapp.mapper.RoleMapper;
import ru.kliuevia.springapp.repository.RoleRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleDto save(RoleDto role) {
        var entity = roleMapper.toEntity(role);
        var savedRole = roleRepository.save(entity);
        return roleMapper.toDto(savedRole);
    }

    public void delete(UUID uuid) {
        roleRepository.deleteById(uuid);
    }

    public RoleDto edit(RoleDto newData) {
        var entity = roleMapper.toEntity(newData);
        var savedRole = roleRepository.save(entity);
        return roleMapper.toDto(savedRole);
    }

    public RoleDto getByUuid(UUID uuid) {
        return roleRepository.findById(uuid)
                .map(roleMapper::toDto)
                .orElseThrow(IllegalArgumentException::new);
    }

    public List<RoleDto> getAll() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toDto)
                .toList();
    }
}
