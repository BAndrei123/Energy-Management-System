package com.example.monitoringspring.dto;


import com.example.monitoringspring.models.Device;
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
