package com.example.monitoringspring.service;

import com.example.monitoringspring.dto.DeviceUserDTO;
import com.example.monitoringspring.dto.DeviceUserRequestDTO;
import com.example.monitoringspring.mapper.DeviceUserMapper;
import com.example.monitoringspring.models.Device;
import com.example.monitoringspring.models.DeviceUser;
import com.example.monitoringspring.repository.DeviceRepository;
import com.example.monitoringspring.repository.DeviceUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DeviceUserService {

    private final DeviceUserMapper deviceUserMapper;
    private final DeviceUserRepository deviceUserRepository;
    private final DeviceRepository deviceRepository;

    public ResponseEntity<DeviceUserDTO> create(DeviceUserRequestDTO deviceUserRequestDTO) {
        Device device = deviceRepository.findByDeviceIdRel(deviceUserRequestDTO.getDeviceId()).get();
        DeviceUser deviceUser = deviceUserMapper.DeviceUserEntity(deviceUserRequestDTO);
        deviceUser.setDevice(device);
        deviceUser.setUserId(deviceUserRequestDTO.getUserId());
        deviceUserRepository.save(deviceUser);
        return ResponseEntity.of(Optional.of(deviceUserMapper.toDeviceUserDto(deviceUser)));

    }

    public List<Long> findUsersId(Long deviceId) {

        Optional<List<DeviceUser>> deviceUsersOpt = deviceUserRepository.findDeviceUsersByDeviceId(deviceId);

        // Return the list of user IDs if present, or an empty list otherwise
        return deviceUsersOpt
                .orElse(Collections.emptyList()) // Handle the case where the Optional is empty
                .stream()
                .map(DeviceUser::getUserId) // Extract the userId from each DeviceUser
                .collect(Collectors.toList());


    }

    @Transactional
    public void deleteAllByUserId(Long userId) {
        deviceUserRepository.deleteDeviceUsersByUserId(userId);
    }


}
