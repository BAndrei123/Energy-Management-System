package com.example.userspring.dto.users.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class EmailResponse {
    private String email;
}
