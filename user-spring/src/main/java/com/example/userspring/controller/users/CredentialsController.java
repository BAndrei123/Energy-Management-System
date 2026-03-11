package com.example.userspring.controller.users;

import com.example.userspring.dto.users.credentials.CredentialsDTO;
import com.example.userspring.dto.users.credentials.CredentialsRequestDTO;

import com.example.userspring.dto.users.user.EmailResponse;
import com.example.userspring.service.users.CredentialsService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.userspring.globals.URLMappings.*;
import static com.example.userspring.globals.users.UrlMappingCredentials.*;

@RestController
@RequestMapping(API_PATH)
@RequiredArgsConstructor
public class CredentialsController {


    private final CredentialsService credentialsService;

    @PostMapping(CREDENTIALS_POST)
    public ResponseEntity<CredentialsDTO> create(@RequestBody @Valid CredentialsRequestDTO credentialsRequestDTO){
    return credentialsService.create(credentialsRequestDTO);
    }


    @GetMapping(CREDENTIALS_EMAIL_GET)
    public ResponseEntity<CredentialsDTO> getEmail(@PathVariable String email){
        return credentialsService.findByEmail(email);
    }

    @PutMapping(CREDENTIALS_PUT)
    public ResponseEntity<CredentialsDTO> update(@RequestBody @Valid CredentialsRequestDTO credentialsRequestDTO){
        return credentialsService.updatePassword(credentialsRequestDTO);
    }

    @DeleteMapping( CREDENTIALS_DELETE)
    public void deleteAccount(@PathVariable String email){

        credentialsService.deleteAccount(email);
    }

    @GetMapping(CREDENTIALS_EMAIL_GET_ALL)
    public ResponseEntity<List<EmailResponse>> getAll(){
        return credentialsService.getEmails();
    }
}
