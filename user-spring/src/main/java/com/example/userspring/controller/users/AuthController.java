package com.example.userspring.controller.users;

import com.example.userspring.dto.users.user.*;
import com.example.userspring.model.users.CredentialsDetailsImpl;
import com.example.userspring.service.users.AuthService;
import com.example.userspring.service.users.JwtUtilsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

import static com.example.userspring.globals.URLMappings.*;


@RequestMapping(AUTH)
@RestController
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;
  private final JwtUtilsService jwtUtilsService;

  @PostMapping(SIGN_IN)
  public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

     Authentication authentication = authService.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtilsService.generateJwtToken(authentication);
    CredentialsDetailsImpl userDetails = (CredentialsDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .toList();


    return ResponseEntity.ok(
        JwtResponse.builder()
            .token(jwt)
            .id(userDetails.getId())
            .username(userDetails.getUsername())
            .email(userDetails.getEmail())
            .roles(roles)
            .build());
  }

  @PostMapping(SIGN_UP)
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (authService.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new UsernameResponse("Username is already taken!"));
    }

    if (authService.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new EmailResponse("Email is already in use!"));
    }


    authService.register(signUpRequest);


    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
}
