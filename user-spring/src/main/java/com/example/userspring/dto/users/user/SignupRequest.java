package com.example.userspring.dto.users.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder


@Getter
@Setter
public class SignupRequest {
  @NotBlank(message = "Username is mandatory")
  private String username;

  @Email(message = "Email is not valid 'example.example@example.com'")
  @NotEmpty(message = "Email cannot be empty")
  private String email;

  @NotBlank(message = "Password is mandatory")
  @Size(min = 8, message = "Password must have at least 8 characters")
  private String password;

  private String roles;
}

/*
{
  username: "blabla",
  password: "123123123",
  email: "alex@email.com",
  roles: [MANAGER,CUSTOMER]
}
 */