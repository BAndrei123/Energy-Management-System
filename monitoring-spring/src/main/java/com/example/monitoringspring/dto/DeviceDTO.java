package com.example.monitoringspring.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDTO {

    private Long id;
    private Long deviceIdRel;
    private Double maximumHourlyConsumption;

}
