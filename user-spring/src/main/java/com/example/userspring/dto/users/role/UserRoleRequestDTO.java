package com.example.userspring.dto.users.role;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleRequestDTO {
    private Long credentialsId;
    private Long roleId;
}
