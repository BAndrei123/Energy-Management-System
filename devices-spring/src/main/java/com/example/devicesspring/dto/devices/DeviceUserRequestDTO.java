package com.example.devicesspring.dto.devices;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceUserRequestDTO {
    private Long deviceId;
    private Long userId;
}
