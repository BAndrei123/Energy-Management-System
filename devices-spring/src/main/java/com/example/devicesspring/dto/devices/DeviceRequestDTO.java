package com.example.devicesspring.dto.devices;

import com.example.devicesspring.models.devices.Device;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceRequestDTO {
    private String name;
    private String description;
    private String address;
    private Double maximumHourlyConsumption;

}
