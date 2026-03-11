package com.example.monitoringspring.service;

import com.example.monitoringspring.dto.DeviceConsumptionDTO;
import com.example.monitoringspring.dto.DeviceConsumptionRequestDTO;
import com.example.monitoringspring.mapper.DeviceConsumptionMapper;
import com.example.monitoringspring.models.Device;
import com.example.monitoringspring.models.DeviceConsumption;
import com.example.monitoringspring.repository.DeviceConsumptionRepository;
import com.example.monitoringspring.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceConsumptionService {

    private final DeviceConsumptionMapper deviceConsumptionMapper;
    private final DeviceConsumptionRepository deviceConsumptionRepository;
    private final DeviceRepository deviceRepository;

    public ResponseEntity<DeviceConsumptionDTO> create(DeviceConsumptionRequestDTO deviceConsumptionRequestDTO) {

        Device device = deviceRepository.findByDeviceIdRel(deviceConsumptionRequestDTO.getDeviceId()).get();
        DeviceConsumption deviceConsumption = deviceConsumptionMapper.DeviceConsumptionEntity(deviceConsumptionRequestDTO);
        deviceConsumption.setDevice(device);
        deviceConsumption.setConsumption(deviceConsumptionRequestDTO.getConsumption());
        deviceConsumption.setTimeConsumption(deviceConsumptionRequestDTO.getTimeConsumption());
        deviceConsumptionRepository.save(deviceConsumption);
        return ResponseEntity.of(Optional.of(deviceConsumptionMapper.toDeviceConsumptionDto(deviceConsumption)));

    }

    public ResponseEntity<List<DeviceConsumptionDTO>> getDeviceConsumptionByDateAndUserId(LocalDate date,  Long userId) {
        List<DeviceConsumption> consumptions = deviceConsumptionRepository.findAllByDateAndUserId(date, userId);

        // Convert the results to DTOs
        List<DeviceConsumptionDTO> dtoList = consumptions.stream()
                .map(deviceConsumptionMapper::toDeviceConsumptionDto)
                .toList();

        // Return the DTOs wrapped in a ResponseEntity
        return ResponseEntity.ok(dtoList);
    }

    public ResponseEntity<List<DeviceConsumptionDTO>> getAllDeviceConsumptionsByDate(LocalDate date) {
        List<DeviceConsumption> consumptions = deviceConsumptionRepository.findAllByDate(date);

        // Convert entities to DTOs
        List<DeviceConsumptionDTO> dtoList = consumptions.stream()
                .map(deviceConsumptionMapper::toDeviceConsumptionDto)
                .toList();

        // Return the DTOs wrapped in a ResponseEntity
        return ResponseEntity.ok(dtoList);
    }





}
