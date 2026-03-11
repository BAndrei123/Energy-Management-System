package com.example.monitoringspring.mapper;


import com.example.monitoringspring.dto.DeviceUserDTO;
import com.example.monitoringspring.dto.DeviceUserRequestDTO;
import com.example.monitoringspring.models.DeviceUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeviceUserMapper {

    DeviceUserDTO toDeviceUserDto(DeviceUser deviceUser);

    DeviceUser DeviceUserEntity(DeviceUserRequestDTO deviceUserRequestDTO);
}
