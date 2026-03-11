package com.example.monitoringspring.repository;

import com.example.monitoringspring.models.DeviceConsumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DeviceConsumptionRepository extends JpaRepository<DeviceConsumption,Long> {

    void deleteAllByDeviceId(Long deviceId);

    @Query("SELECT dc FROM DeviceConsumption dc WHERE dc.device.deviceIdRel = :deviceIdRel AND DATE(dc.timeConsumption) = :date")
    List<DeviceConsumption> findAllByDeviceIdRelAndDate(
            @Param("deviceIdRel") Long deviceIdRel,
            @Param("date") LocalDate date
    );

    @Query("SELECT dc FROM DeviceConsumption dc WHERE DATE(dc.timeConsumption) = :date")
    List<DeviceConsumption> findAllByDate(@Param("date") LocalDate date);
    @Query("SELECT dc FROM DeviceConsumption dc WHERE DATE(dc.timeConsumption) = :date AND dc.device IN " +
            "(SELECT du.device FROM DeviceUser du WHERE du.userId = :userId)")
    List<DeviceConsumption> findAllByDateAndUserId(@Param("date") LocalDate date, @Param("userId") Long userId);

}
