package com.example.devicesspring.controller.devices;

import com.example.devicesspring.dto.devices.DeviceUserDTO;
import com.example.devicesspring.dto.devices.DeviceUserRequestDTO;
import com.example.devicesspring.service.DeviceUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.example.devicesspring.globals.URLMappings.API_PATH;
import static com.example.devicesspring.globals.devices.UrlMappingDeviceUser.*;

@Controller
@RequestMapping(API_PATH)
@RequiredArgsConstructor
@CrossOrigin
public class DeviceUserController {

    private final DeviceUserService deviceUserService;

    @PostMapping(DEVICE_USER_POST)
    public ResponseEntity<DeviceUserDTO> create(@RequestBody DeviceUserRequestDTO deviceUserRequestDTO) {
        return deviceUserService.create(deviceUserRequestDTO);
    }

    @GetMapping(DEVICE_USER_GET_ALL_USER_ID)
    public ResponseEntity<Page<DeviceUserDTO>> findAllDevicesByUserId(@PathVariable Long id, @PathVariable int page) {
        return deviceUserService.findAll(id,PageRequest.of(page,15));
    }

    @DeleteMapping(DEVICE_USER_DELETE)
    public void delete(@PathVariable Long id) {
        deviceUserService.deleteAllByUserId(id);
    }
}
