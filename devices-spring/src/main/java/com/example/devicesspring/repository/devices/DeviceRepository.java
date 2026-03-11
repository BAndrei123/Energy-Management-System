package com.example.devicesspring.repository.devices;

import com.example.devicesspring.models.devices.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device,Long> {
    boolean existsByName(String name);
    Optional<Device> findByName(String name);
}
