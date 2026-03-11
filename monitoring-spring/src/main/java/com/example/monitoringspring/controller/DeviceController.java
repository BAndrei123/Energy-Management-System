package com.example.monitoringspring.controller;

import com.example.monitoringspring.dto.DeviceDTO;
import com.example.monitoringspring.dto.DeviceRequestDTO;
import com.example.monitoringspring.service.DeviceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.monitoringspring.globals.URLMappings.API_PATH;
import static com.example.monitoringspring.globals.UrlMappingDevice.*;

@RestController
@RequestMapping(API_PATH)
@RequiredArgsConstructor
@CrossOrigin
public class DeviceController {

    private final DeviceService deviceService;

    @PostMapping(DEVICE_POST)
    public ResponseEntity<?> create(@RequestBody @Valid DeviceRequestDTO deviceRequestDTO) {

        return deviceService.createDevice(deviceRequestDTO);

    }

    @GetMapping(DEVICE_GET)
    public ResponseEntity<DeviceDTO> findByID(@PathVariable Long id){
        return deviceService.findDeviceIdRel(id);
    }

    @PutMapping(DEVICE_PUT)
    public ResponseEntity<DeviceDTO> update(@RequestBody @Valid DeviceRequestDTO deviceRequestDTO) {
        return deviceService.updateDevice(deviceRequestDTO);
    }

    @DeleteMapping(DEVICE_DELETE)
    public void delete(@PathVariable Long id) {
        deviceService.deleteDevice(id);
    }
}
