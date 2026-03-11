package com.example.monitoringspring.mapper;


import com.example.monitoringspring.dto.DeviceConsumptionDTO;
import com.example.monitoringspring.dto.DeviceConsumptionRequestDTO;
import com.example.monitoringspring.models.DeviceConsumption;
import org.mapstruct.Mapper;

@Mapper( componentModel = "spring")
public interface DeviceConsumptionMapper {

    DeviceConsumptionDTO toDeviceConsumptionDto(DeviceConsumption deviceConsumption);

    DeviceConsumption DeviceConsumptionEntity(DeviceConsumptionRequestDTO deviceConsumptionRequestDTO);

}
