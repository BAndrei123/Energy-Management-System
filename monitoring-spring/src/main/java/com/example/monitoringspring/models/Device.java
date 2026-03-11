package com.example.monitoringspring.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "devices", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"device_id_rel"})
})
public class Device {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "device_id_rel", nullable = false)
    private Long deviceIdRel;

    @Column(name = "maximum_hourly_energy_consumption")
    private Double maximumHourlyConsumption;
}
