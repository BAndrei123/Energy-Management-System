package com.example.monitoringspring.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "devices_consumption")
public class DeviceConsumption {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "device_id", nullable = false ,referencedColumnName = "id")
    private Device device;

    @Column(name = "time_consumption", nullable = false)
    private LocalDateTime timeConsumption;

    @Column(name = "consumption")
    private Double consumption;
}
