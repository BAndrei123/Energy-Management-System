package com.example.userspring.model.users;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Table(name = "user_details")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "phone_number",nullable = false)
    private Long phoneNumber;
    @Column(name = "date_of_birth",nullable = false)
    private LocalDate dateOfBirth;
    @Column(name = "delivery_address",nullable = false)
    private String deliveryAddress;
    @Column(name = "postal_code",nullable = false)
    private Integer postalCode;

    @OneToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "credentials_id",referencedColumnName = "id")
    private Credentials credentials;
}
