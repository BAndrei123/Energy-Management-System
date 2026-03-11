package com.example.userspring.config;

import com.example.userspring.model.users.ERole;
import com.example.userspring.model.users.Role;
import com.example.userspring.repository.users.RoleRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
@RequiredArgsConstructor
public class Bootstrap {

  private final RoleRepository roleRepository;

  @EventListener(ApplicationReadyEvent.class)
  public void bootstrapData() {
    for (ERole value : ERole.values()) {
      if (roleRepository.findByRole(value).isEmpty()) {
        roleRepository.save(
            Role.builder().role(value).build()
        );
      }
    }

  }
}

