package com.example.monitoringspring.dto;

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
