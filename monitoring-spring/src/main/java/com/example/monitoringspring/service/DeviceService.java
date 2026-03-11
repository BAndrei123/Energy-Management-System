package com.example.monitoringspring.service;

import com.example.monitoringspring.dto.DeviceDTO;
import com.example.monitoringspring.dto.DeviceRequestDTO;
import com.example.monitoringspring.mapper.DeviceMapper;
import com.example.monitoringspring.models.Device;
import com.example.monitoringspring.repository.DeviceConsumptionRepository;
import com.example.monitoringspring.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceMapper deviceMapper;
    private final DeviceRepository deviceRepository;
    private final DeviceConsumptionRepository deviceConsumptionRepository;

    public ResponseEntity<DeviceDTO> createDevice(DeviceRequestDTO deviceRequestDTO) {

        if(deviceRequestDTO == null)
            return ResponseEntity.badRequest().build();


        Device device = deviceMapper.toDeviceEntity(deviceRequestDTO);
        deviceRepository.save(device);
        return ResponseEntity.ok(deviceMapper.

                toDeviceDto(device));
    }


    public ResponseEntity<DeviceDTO> findById(Long id) {
        return deviceRepository.findById(id)
                .map(deviceMapper::toDeviceDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<DeviceDTO> findDeviceIdRel(Long deviceIdRel) {
        return deviceRepository.findByDeviceIdRel(deviceIdRel)
                .map(deviceMapper::toDeviceDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<DeviceDTO> updateDevice(DeviceRequestDTO deviceRequestDTO) {
        if(deviceRequestDTO == null ){
            return ResponseEntity.badRequest().build();
        }
        Optional<Device> deviceOptional = deviceRepository.findByDeviceIdRel(deviceRequestDTO.getDeviceIdRel());
        if(deviceOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Device device = deviceOptional.get();
        device.setMaximumHourlyConsumption(deviceRequestDTO.getMaximumHourlyConsumption());
        return ResponseEntity.ok(deviceMapper.toDeviceDto(deviceRepository.save(device)));
    }

    @Transactional
    public void deleteDevice(Long deviceIdRel) {

      //  Device device = deviceRepository.findByDeviceIdRel(deviceIdRel).get();

        deviceConsumptionRepository.deleteAllByDeviceId(deviceIdRel);

        deviceRepository.deleteAllByDeviceIdRel(deviceIdRel);
    }


}
