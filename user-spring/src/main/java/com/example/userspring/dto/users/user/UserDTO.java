package com.example.userspring.dto.users.user;

import com.example.userspring.model.users.Credentials;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private Integer phoneNumber;
    private LocalDate dateOfBirth;
    private String deliveryAddress;
    private Integer postalCode;
    private Credentials credentials;

}
