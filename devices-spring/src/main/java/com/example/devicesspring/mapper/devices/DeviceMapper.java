package com.example.devicesspring.mapper.devices;

import com.example.devicesspring.dto.devices.DeviceDTO;
import com.example.devicesspring.dto.devices.DeviceRequestDTO;
import com.example.devicesspring.models.devices.Device;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeviceMapper {

    DeviceDTO toDeviceDto(Device device);

    DeviceRequestDTO toRequestDTO(DeviceDTO deviceDTO);

    Device toDeviceEntity(DeviceRequestDTO deviceRequestDTO);
}
