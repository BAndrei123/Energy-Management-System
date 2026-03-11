package com.example.devicesspring.mapper.devices;

import com.example.devicesspring.dto.devices.DeviceUserDTO;
import com.example.devicesspring.dto.devices.DeviceUserRequestDTO;
import com.example.devicesspring.models.devices.DeviceUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeviceUserMapper {

    DeviceUserDTO toDeviceUserDto(DeviceUser deviceUser);

    DeviceUser DeviceUserEntity(DeviceUserRequestDTO deviceUserRequestDTO);
}
