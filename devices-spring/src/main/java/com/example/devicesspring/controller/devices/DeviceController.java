package com.example.devicesspring.controller.devices;

import com.example.devicesspring.dto.devices.DeviceDTO;
import com.example.devicesspring.dto.devices.DeviceNameDTO;
import com.example.devicesspring.dto.devices.DeviceRequestDTO;
import com.example.devicesspring.service.DeviceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.devicesspring.globals.URLMappings.API_PATH;
import static com.example.devicesspring.globals.devices.UrlMappingDevice.*;

@RestController
@RequestMapping(API_PATH)
@RequiredArgsConstructor
@CrossOrigin
public class DeviceController {

    private final DeviceService deviceService;

    @PostMapping(DEVICE_POST)
    public ResponseEntity<?> create(@RequestBody @Valid DeviceRequestDTO deviceRequestDTO) {
        if(deviceService.existsDeviceByName(deviceRequestDTO.getName())) {
            return ResponseEntity.badRequest().body(new DeviceNameDTO("Device already exists"));
        }
        return deviceService.createDevice(deviceRequestDTO);

    }

    @GetMapping(DEVICE_GET)
    public ResponseEntity<DeviceDTO> findByID(@PathVariable Long id){
        return deviceService.findById(id);
    }

    @GetMapping(DEVICE_GET_NAME)
    public ResponseEntity<DeviceDTO> findByName(@PathVariable String name){
        return deviceService.findByName(name);
    }

    @PutMapping(DEVICE_PUT)
    public ResponseEntity<DeviceDTO> update(@RequestBody @Valid DeviceRequestDTO deviceRequestDTO, @PathVariable Long id) {
        return deviceService.updateDevice(id, deviceRequestDTO);
    }

    @DeleteMapping(DEVICE_DELETE)
    public void delete(@PathVariable Long id) {
        deviceService.deleteDevice(id);
    }

    @GetMapping(DEVICE_GET_ALL)
    public ResponseEntity<List<DeviceDTO>> findAll() {
       return deviceService.findAllDevices();
    }
}
