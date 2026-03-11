package com.example.userspring.dto.users.credentials;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CredentialsRequestDTO {
    private String email;
    private String password;
}
