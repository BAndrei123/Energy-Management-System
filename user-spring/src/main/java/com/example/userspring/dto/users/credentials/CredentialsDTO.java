package com.example.userspring.dto.users.credentials;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CredentialsDTO {
    private Long id;
    private String email;

    private String password;
}
