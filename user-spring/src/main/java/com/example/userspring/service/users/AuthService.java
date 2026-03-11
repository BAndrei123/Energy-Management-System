package com.example.userspring.service.users;

import com.example.userspring.dto.users.user.SignupRequest;
import com.example.userspring.model.users.Credentials;
import com.example.userspring.model.users.ERole;
import com.example.userspring.model.users.Role;
import com.example.userspring.model.users.UserRole;
import com.example.userspring.repository.users.CredentialsRepository;
import com.example.userspring.repository.users.RoleRepository;
import com.example.userspring.repository.users.UserRoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

  private final CredentialsRepository userRepository;
  private final RoleRepository roleRepository;
  private final UserRoleRepository userRoleRepository;
  private final PasswordEncoder encoder;
  private final AuthenticationManager authenticationManager;

  public boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  @Transactional
  public void register(SignupRequest signUpRequest) {
    Credentials user = Credentials.builder()
        .username(signUpRequest.getUsername())
        .password(encoder.encode(signUpRequest.getPassword()))
        .email(signUpRequest.getEmail())
        .build();
    userRepository.save(user);
    String rolesStr = signUpRequest.getRoles();


    if (rolesStr == null) {
      Role defaultRole = roleRepository.findByRole(ERole.USER).get();
      Credentials credentials = userRepository.findByEmail(signUpRequest.getEmail()).get();
      UserRole userRole = UserRole.builder().role(defaultRole).credentials(credentials).build();
      userRoleRepository.save(userRole);
    }else{
      Role role = roleRepository.findByRole(ERole.valueOf(rolesStr)).get();
      Credentials credentials = userRepository.findByEmail(signUpRequest.getEmail()).get();
      UserRole userRole = UserRole.builder().role(role).credentials(credentials).build();
      userRoleRepository.save(userRole);
    }


  }

  public Authentication authenticate(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
    return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
  }
}
