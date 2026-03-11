package com.example.devicesspring.dto.devices;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDTO {
    private Long id;
    private String name;
    private String description;
    private String address;
    private Double maximumHourlyConsumption;
}
