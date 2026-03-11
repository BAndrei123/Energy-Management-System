package com.example.monitoringspring.repository;


import com.example.monitoringspring.models.DeviceUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceUserRepository extends JpaRepository<DeviceUser,Long> {
    Optional <DeviceUser> findDeviceUsersByUserId (Long userId);
    Page<DeviceUser> findDeviceUsersByUserId (Long userId, Pageable pageable);
    List<DeviceUser> findDevicesUsersByUserId(Long userId);
    void deleteDeviceUsersByDeviceId (Long deviceId);
    void deleteDeviceUsersByUserId (Long userId);

    Optional<List<DeviceUser>> findDeviceUsersByDeviceId (Long deviceId);
}
