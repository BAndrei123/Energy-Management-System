package com.example.devicesspring.models.devices;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "device_user")
public class DeviceUser {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "device_id", nullable = false ,referencedColumnName = "id")
    private Device device;
}
