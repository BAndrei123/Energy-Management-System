package com.example.monitoringspring.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceRequestDTO {

    private Long deviceIdRel;
    private Double maximumHourlyConsumption;

}
