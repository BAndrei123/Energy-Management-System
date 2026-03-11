package com.example.monitoringspring.controller;

import com.example.monitoringspring.dto.DeviceConsumptionDTO;
import com.example.monitoringspring.service.DeviceConsumptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.example.monitoringspring.globals.URLMappings.API_PATH;
import static com.example.monitoringspring.globals.UrlMappingDeviceConsumption.DEVICE_CONSUMPTION_GET_CONSUMPTION;


@RestController
@RequestMapping(API_PATH)
@RequiredArgsConstructor
@CrossOrigin
public class DeviceConsumptionController {

    private final DeviceConsumptionService deviceConsumptionService;

    @GetMapping(DEVICE_CONSUMPTION_GET_CONSUMPTION)
    public ResponseEntity<List<DeviceConsumptionDTO>> getDeviceConsumptionByUserId(@PathVariable LocalDate date, @PathVariable Long id) {
       return  deviceConsumptionService.getDeviceConsumptionByDateAndUserId(date,id);
    }

    @GetMapping("/DEVICE_CONSUMPTION/{date}")
    public ResponseEntity<List<DeviceConsumptionDTO>> getAllDeviceConsumptions(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return deviceConsumptionService.getAllDeviceConsumptionsByDate(date);
    }

}
