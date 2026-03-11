package com.example.devicesspring.models.devices;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "devices", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name"})
})
public class Device {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description",nullable = false)
    private String description;

    @Column(name = "address",nullable = false)
    private String address;

    @Column(name = "maximum_hourly_energy_consumption")
    private Double maximumHourlyConsumption;
}
