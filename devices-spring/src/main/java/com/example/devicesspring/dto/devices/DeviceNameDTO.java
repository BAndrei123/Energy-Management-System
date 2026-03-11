package com.example.devicesspring.dto.devices;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class DeviceNameDTO {
    private String deviceName;
}
