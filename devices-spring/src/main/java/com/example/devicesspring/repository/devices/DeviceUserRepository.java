package com.example.devicesspring.repository.devices;

import com.example.devicesspring.models.devices.DeviceUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceUserRepository extends JpaRepository<DeviceUser,Long> {
    Optional <DeviceUser> findDeviceUsersByUserId (Long userId);
    Page<DeviceUser> findDeviceUsersByUserId (Long userId, Pageable pageable);

    void deleteDeviceUsersByDeviceId (Long deviceId);
    void deleteDeviceUsersByUserId (Long userId);

}
