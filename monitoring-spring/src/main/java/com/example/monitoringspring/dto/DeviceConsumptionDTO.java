package com.example.monitoringspring.dto;

import com.example.monitoringspring.models.Device;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceConsumptionDTO {
    private Long id;
    private Device device;
    private LocalDateTime timeConsumption;
    private Double consumption;
}
