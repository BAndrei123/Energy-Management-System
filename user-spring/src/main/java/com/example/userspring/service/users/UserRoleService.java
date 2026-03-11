package com.example.userspring.service.users;

import com.example.userspring.dto.users.role.UserRoleDTO;
import com.example.userspring.dto.users.role.UserRoleRequestDTO;
import com.example.userspring.mapper.users.UserRoleMapper;
import com.example.userspring.model.users.Credentials;
import com.example.userspring.model.users.ERole;
import com.example.userspring.model.users.Role;
import com.example.userspring.model.users.UserRole;
import com.example.userspring.repository.users.CredentialsRepository;
import com.example.userspring.repository.users.RoleRepository;
import com.example.userspring.repository.users.UserRoleRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserRoleService {
    private final UserRoleMapper userRoleMapper;
    private final UserRoleRepository userRoleRepository;
    private final CredentialsRepository credentialsRepository;
    private final RoleRepository roleRepository;
    public ResponseEntity<UserRoleDTO> create(UserRoleRequestDTO userRoleRequestDTO){
        Credentials credentials= credentialsRepository.findById(userRoleRequestDTO.getCredentialsId()).get();
        Role role  = roleRepository.findById(userRoleRequestDTO.getRoleId()).get();
        UserRole userRole = userRoleMapper.UserRoleEntity(userRoleRequestDTO);
        userRole.setRole(role);
        userRole.setCredentials(credentials);
        userRoleRepository.save(userRole);
        return ResponseEntity.of(Optional.of(userRoleMapper.toUserRoleDTO(userRole)));
    }

    public ResponseEntity<UserRoleDTO> update(UserRoleRequestDTO userRoleRequestDTO){
        /*Credentials credentials= credentialsRepository.findById(userRoleRequestDTO.getCredentialsId()).get();
        Role role  = roleRepository.findById(userRoleRequestDTO.getRoleId()).get();
        UserRole userRole = userRoleMapper.UserRoleEntity(userRoleRequestDTO);
        userRole.setRole(role);
        userRole.setCredentials(credentials);
        userRoleRepository.save(userRole);*/
        UserRole userRole = userRoleRepository.findByCredentialsId(userRoleRequestDTO.getCredentialsId()).get();
        Role role = roleRepository.findById(userRoleRequestDTO.getRoleId()).get();
        userRole.setRole(role);

        userRoleRepository.save(userRole);
        return ResponseEntity.of(Optional.of(userRoleMapper.toUserRoleDTO(userRole)));
    }

    public ResponseEntity<UserRoleDTO> findByCredentials(String email){
        Credentials credentials = credentialsRepository.findByEmail(email).get();
        UserRole userRole = userRoleRepository.findByCredentialsId(credentials.getId()).get();
        return ResponseEntity.of(Optional.of(userRoleMapper.toUserRoleDTO(userRole)));
    }

    @Transactional
    public void delete(String email){
        Credentials credentials = credentialsRepository.findByEmail(email).get();
        UserRole userRole = userRoleRepository.findByCredentialsId(credentials.getId()).get();
        userRoleRepository.delete(userRole);
    }

    public ResponseEntity<UserRoleDTO> findByUsername(String username) {
        Credentials credentials = credentialsRepository.findByUsername(username).get();
        UserRole userRole = userRoleRepository.findByCredentialsId(credentials.getId()).get();
        return ResponseEntity.of(Optional.of(userRoleMapper.toUserRoleDTO(userRole)));
    }

    public ResponseEntity<List<UserRoleDTO>> findAllByRole(String roleName){
        Optional<Role> role = roleRepository.findByRole(ERole.valueOf(roleName));
        if(role.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        List<UserRole> userRoles = userRoleRepository.findAllByRole(role.get());
        List<UserRoleDTO> userRoleDTOs = userRoles
                .stream()
                .map(userRoleMapper::toUserRoleDTO)
                .toList();
        return ResponseEntity.ok(userRoleDTOs);
    }
}
