package com.example.monitoringspring.repository;


import com.example.monitoringspring.dto.DeviceDTO;
import com.example.monitoringspring.models.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device,Long> {

    Optional<Device> findByDeviceIdRel(Long id);

    void deleteAllByDeviceIdRel(Long id);
}
