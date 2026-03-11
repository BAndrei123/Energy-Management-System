package com.example.userspring.model.users;

import jakarta.persistence.*;
import lombok.*;



@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Table(name = "user_role")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "credentials_id",referencedColumnName = "id")
    private Credentials credentials;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id",referencedColumnName = "id")
    private Role role;
}
