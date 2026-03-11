package com.example.devicesspring.service;

import com.example.devicesspring.dto.devices.DeviceUserDTO;
import com.example.devicesspring.dto.devices.DeviceUserRequestDTO;
import com.example.devicesspring.mapper.devices.DeviceUserMapper;
import com.example.devicesspring.models.devices.Device;
import com.example.devicesspring.models.devices.DeviceUser;
import com.example.devicesspring.repository.devices.DeviceRepository;
import com.example.devicesspring.repository.devices.DeviceUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceUserService {

    private final DeviceUserMapper deviceUserMapper;
    private final DeviceUserRepository deviceUserRepository;
    private final DeviceRepository deviceRepository;
    private final DeviceUserEventPublisher deviceUserEventPublisher;

    public ResponseEntity<DeviceUserDTO> create(DeviceUserRequestDTO deviceUserRequestDTO) {
        Device device = deviceRepository.findById(deviceUserRequestDTO.getDeviceId()).get();
        DeviceUser deviceUser = deviceUserMapper.DeviceUserEntity(deviceUserRequestDTO);
        deviceUser.setDevice(device);
        deviceUser.setUserId(deviceUserRequestDTO.getUserId());
        deviceUserRepository.save(deviceUser);
        deviceUserEventPublisher.sendMapedDevice(deviceUser.getUserId(),deviceUser.getDevice().getId());
        return ResponseEntity.of(Optional.of(deviceUserMapper.toDeviceUserDto(deviceUser)));

    }

    public ResponseEntity<Page<DeviceUserDTO>> findAll(Long userId,Pageable pageable) {
        Page<DeviceUser> deviceUsers = deviceUserRepository.findDeviceUsersByUserId(userId,pageable);
        Page<DeviceUserDTO> deviceUserDTOPage = deviceUsers.map(deviceUserMapper::toDeviceUserDto);
        return ResponseEntity.of(Optional.of(deviceUserDTOPage));
    }

    @Transactional
    public void deleteAllByUserId(Long userId) {
        deviceUserEventPublisher.sendMapedDeviceDelete(userId);
        deviceUserRepository.deleteDeviceUsersByUserId(userId);
    }



}
