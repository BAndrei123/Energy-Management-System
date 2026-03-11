package com.example.userspring.service.users;

import com.example.userspring.dto.users.user.UserDTO;
import com.example.userspring.dto.users.user.UserRequestDTO;
import com.example.userspring.mapper.users.UserMapper;
import com.example.userspring.model.users.Credentials;
import com.example.userspring.model.users.User;
import com.example.userspring.model.users.rabbitmq.UserEventPublisher;
import com.example.userspring.repository.users.CredentialsRepository;
import com.example.userspring.repository.users.UserRepository;
import com.example.userspring.repository.users.UserRoleRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final CredentialsRepository credentialsRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserEventPublisher userEventPublisher;

    @Transactional
    public ResponseEntity<UserDTO> create (UserRequestDTO userRequestDTO){

        User user = userMapper.userEntity(userRequestDTO);

        // Check if the Optional is empty before calling get()
        Optional<Credentials> optionalCredentials = credentialsRepository.findByEmail(userRequestDTO.getEmail());
        if (optionalCredentials.isEmpty()) {
            return ResponseEntity.notFound().build(); // Or handle the case appropriately
        }

        Credentials credentials = optionalCredentials.get();
        user.setCredentials(credentials);
        userRepository.save(user);
        return ResponseEntity.ok(userMapper.toUserDTO(user));
    }

    public ResponseEntity<UserDTO> findByEmail(String email){
        Credentials credentials = credentialsRepository.findByEmail(email).get();
        return userRepository.findByCredentialsId(credentials.getId())
                .map(userMapper::toUserDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }
    public ResponseEntity<UserDTO> findByUserName(String username){
        Credentials credentials = credentialsRepository.findByUsername(username).get();
        return userRepository.findByCredentialsId(credentials.getId())
                .map(userMapper::toUserDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<UserDTO> update(UserRequestDTO userRequestDTO){
        Optional<Credentials> optionalCredentials = credentialsRepository.findByEmail(userRequestDTO.getEmail());
        if (optionalCredentials.isEmpty()) {
            return ResponseEntity.notFound().build(); // Or handle the case appropriately
        }

        Long credentialsId = optionalCredentials.get().getId();

        // Check if the Optional is empty before calling get() in userRepository.findByCredentialsId
        Optional<User> optionalUser = userRepository.findByCredentialsId(credentialsId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build(); // Or handle the case appropriately
        }

        User user = optionalUser.get();
        user.setName(userRequestDTO.getName());
        user.setDateOfBirth(userRequestDTO.getDateOfBirth());
        user.setDeliveryAddress(userRequestDTO.getDeliveryAddress());
        user.setPostalCode(userRequestDTO.getPostalCode());
        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(userMapper.toUserDTO(updatedUser));
    }

    @Transactional
    public void deleteUser(String email){
        Credentials credentials = credentialsRepository.findByEmail(email).get();

        userRepository.deleteUserByCredentialsId(credentials.getId());
        userRoleRepository.deleteByCredentialsId(credentials.getId());
        credentialsRepository.delete(credentials);
        userEventPublisher.sendUserDeletedEvent(credentials.getId());
    }
}
