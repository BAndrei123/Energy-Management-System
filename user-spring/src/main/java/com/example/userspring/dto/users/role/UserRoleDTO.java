package com.example.userspring.dto.users.role;

import com.example.userspring.model.users.Credentials;
import com.example.userspring.model.users.Role;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleDTO {
    private Long id;
    private Credentials credentials;
    private Role role;
}
