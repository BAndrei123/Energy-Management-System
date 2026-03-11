package com.example.userspring.dto.users.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;

    private Long phoneNumber;


    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Delivery address is required")
    private String deliveryAddress;

    private Integer postalCode;

    private String email;
}
