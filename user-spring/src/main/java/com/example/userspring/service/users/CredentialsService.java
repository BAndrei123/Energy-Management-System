package com.example.userspring.service.users;

import com.example.userspring.dto.users.credentials.CredentialsDTO;
import com.example.userspring.dto.users.credentials.CredentialsRequestDTO;
import com.example.userspring.dto.users.user.EmailResponse;
import com.example.userspring.mapper.users.CredentialsMapper;
import com.example.userspring.model.users.Credentials;
import com.example.userspring.model.users.rabbitmq.UserEventPublisher;
import com.example.userspring.repository.users.CredentialsRepository;
import com.example.userspring.repository.users.UserRepository;
import com.example.userspring.repository.users.UserRoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CredentialsService {
    private final CredentialsRepository credentialsRepository;
    private final CredentialsMapper credentialsMapper;
    private final UserEventPublisher userEventPublisher;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public ResponseEntity<CredentialsDTO> create (CredentialsRequestDTO credentialsRequestDTO){
        Credentials credentials = credentialsMapper.credentialsEntity(credentialsRequestDTO);

        credentialsRepository.save(credentials);
        return ResponseEntity.of(Optional.of(credentialsMapper.toCredentialsDTO(credentials)));
    }

    public ResponseEntity<CredentialsDTO> findByEmailAndPassword(CredentialsRequestDTO credentialsRequestDTO){
        return credentialsRepository.findByEmailAndPassword(credentialsRequestDTO.getEmail(),credentialsRequestDTO.getPassword())
                .map(credentialsMapper::toCredentialsDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    public ResponseEntity<CredentialsDTO> findByEmail(String email){
        return credentialsRepository.findByEmail(email)
                .map(credentialsMapper::toCredentialsDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    public ResponseEntity<CredentialsDTO> updatePassword(CredentialsRequestDTO credentialsRequestDTO) {

        String email = credentialsRequestDTO.getEmail();
        String newPassword = credentialsRequestDTO.getPassword();

        Optional<Credentials> optionalCredentials = credentialsRepository.findByEmail(email);
        if (optionalCredentials.isPresent()) {
            Credentials credentials = optionalCredentials.get();
            credentials.setPassword(newPassword);
            credentialsRepository.save(credentials);
            return ResponseEntity.ok(credentialsMapper.toCredentialsDTO(credentials));
        } else {
            return ResponseEntity.notFound().build();

        }
    }

    @Transactional
    public void deleteAccount(String email){
        Credentials credentials = credentialsRepository.findByEmail(email).get();
        userRepository.deleteUserByCredentialsId(credentials.getId());
        userRoleRepository.deleteByCredentialsId(credentials.getId());
        credentialsRepository.delete(credentials);
        userEventPublisher.sendUserDeletedEvent(credentials.getId());
    }

    public ResponseEntity<List<EmailResponse>> getEmails(){
        List<Credentials> credentials = credentialsRepository.findAll();

        List<EmailResponse> emailResponses = credentials.stream().map(credential -> new EmailResponse(credential.getEmail())).toList();
        return ResponseEntity.ok(emailResponses);
    }
}
