package com.example.devicesspring.service;

import com.example.devicesspring.dto.devices.DeviceDTO;
import com.example.devicesspring.dto.devices.DeviceRequestDTO;
import com.example.devicesspring.mapper.devices.DeviceMapper;
import com.example.devicesspring.models.devices.Device;
import com.example.devicesspring.repository.devices.DeviceRepository;
import com.example.devicesspring.repository.devices.DeviceUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceMapper deviceMapper;
    private final DeviceRepository deviceRepository;
    private final DeviceUserRepository userRepository;
    private final DeviceUserRepository deviceUserRepository;
    private final DeviceEventPublisher deviceEventPublisher;

    public ResponseEntity<DeviceDTO> createDevice(DeviceRequestDTO deviceRequestDTO) {

        if(deviceRequestDTO == null)
            return ResponseEntity.badRequest().build();


        Device device = deviceMapper.toDeviceEntity(deviceRequestDTO);
        Device deviceSaved = deviceRepository.save(device);

        deviceEventPublisher.sendDeviceCreatedEvent(
                deviceSaved.getId().toString(), // Replace with actual ID getter
                deviceRequestDTO.getMaximumHourlyConsumption()
        );


        return ResponseEntity.ok(deviceMapper.
                toDeviceDto(device));
    }

    public ResponseEntity<DeviceDTO> findById(Long id) {
        return deviceRepository.findById(id)
                .map(deviceMapper::toDeviceDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<DeviceDTO> findByName(String name) {
        return deviceRepository.findByName(name)
                .map(deviceMapper::toDeviceDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<DeviceDTO> updateDevice(Long id, DeviceRequestDTO deviceRequestDTO) {
        if(deviceRequestDTO == null ){
            return ResponseEntity.badRequest().build();
        }

        Optional<Device> deviceOptional = deviceRepository.findById(id);
        if(deviceOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Device device = deviceOptional.get();
        device.setName(deviceRequestDTO.getName());
        device.setDescription(deviceRequestDTO.getDescription());
        device.setAddress(deviceRequestDTO.getAddress());
        device.setMaximumHourlyConsumption(deviceRequestDTO.getMaximumHourlyConsumption());
        Device updatedDevice = deviceRepository.save(device);

        deviceEventPublisher.sendDeviceUpdatedEvent(updatedDevice.getId().toString(), updatedDevice.getMaximumHourlyConsumption());

        return ResponseEntity.ok(deviceMapper.toDeviceDto(updatedDevice));

    }
    @Transactional
    public void deleteDevice(Long id) {
        deviceUserRepository.deleteDeviceUsersByDeviceId(id);
        deviceRepository.deleteById(id);
        deviceEventPublisher.sendDeviceDeletedEvent(id);
    }


    public boolean existsDeviceByName(String name) {
        return deviceRepository.existsByName(name);
    }

    public ResponseEntity<List<DeviceDTO>> findAllDevices() {
        return ResponseEntity.ok(deviceRepository.findAll()
                .stream()
                .map(deviceMapper::toDeviceDto)
                .collect(Collectors.toList()));

    }


}
