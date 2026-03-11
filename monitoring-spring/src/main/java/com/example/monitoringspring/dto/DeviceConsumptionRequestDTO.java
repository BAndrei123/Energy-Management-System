package com.example.monitoringspring.dto;

import com.example.monitoringspring.models.Device;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class DeviceConsumptionRequestDTO {
    private Long deviceId;
    private LocalDateTime timeConsumption;
    private Double consumption;
}
