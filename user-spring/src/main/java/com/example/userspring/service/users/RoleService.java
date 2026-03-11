package com.example.userspring.service.users;

import com.example.userspring.dto.users.role.RoleDTO;
import com.example.userspring.dto.users.role.RoleRequestDTO;
import com.example.userspring.mapper.users.RoleMapper;
import com.example.userspring.model.users.ERole;
import com.example.userspring.model.users.Role;
import com.example.userspring.repository.users.RoleRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;

    public ResponseEntity<RoleDTO> create(RoleRequestDTO roleRequestDTO){
        if (roleRequestDTO == null || roleRequestDTO.getRole() == null) {
            return ResponseEntity.badRequest().build();
        }

        Role role = roleMapper.roleEntity(roleRequestDTO);
        roleRepository.save(role);
        return ResponseEntity.ok(roleMapper.toRoleDTO(role));
    }

    public ResponseEntity<RoleDTO> findById(Long id){
        return roleRepository.findById(id)
                .map(roleMapper::toRoleDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<RoleDTO> updateRole(RoleRequestDTO roleRequestDTO, Long id){
        if (roleRequestDTO == null || roleRequestDTO.getRole() == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Role role = optionalRole.get();
        role.setRole(ERole.valueOf(roleRequestDTO.getRole()));
        Role roleUpdate = roleRepository.save(role);
        return ResponseEntity.ok(roleMapper.toRoleDTO(roleUpdate));
    }

    public void delete(Long id){
        roleRepository.deleteById(id);
    }

}
