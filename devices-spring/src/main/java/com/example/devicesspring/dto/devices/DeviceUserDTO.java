package com.example.devicesspring.dto.devices;

import com.example.devicesspring.models.devices.Device;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceUserDTO {
    private Long id;
    private Device device;
    private Long userId;

}
