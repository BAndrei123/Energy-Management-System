package com.example.monitoringspring.mapper;

import com.example.monitoringspring.dto.DeviceDTO;
import com.example.monitoringspring.dto.DeviceRequestDTO;
import com.example.monitoringspring.models.Device;
import org.mapstruct.Mapper;

@Mapper( componentModel = "spring")
public interface DeviceMapper {

    DeviceDTO toDeviceDto(Device device);

    DeviceRequestDTO toRequestDTO(DeviceDTO deviceDTO);

    Device toDeviceEntity(DeviceRequestDTO deviceRequestDTO);
}
