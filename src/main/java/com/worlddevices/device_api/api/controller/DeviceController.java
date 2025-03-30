package com.worlddevices.device_api.api.controller;

import com.worlddevices.device_api.api.dto.request.DeviceRequest;
import com.worlddevices.device_api.api.dto.response.DeviceResponse;
import com.worlddevices.device_api.core.service.IDeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/devices")
public class DeviceController {

    private final IDeviceService service;

    public DeviceController(IDeviceService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DeviceResponse> saveDevice(@RequestBody DeviceRequest device) {
        log.info("Request to save device: {}", device.getName());
        return service.save(device);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceResponse> updateDeviceById(@PathVariable Long id, @RequestBody DeviceRequest device) {
        log.info("Request to update device by id: {} with details: {}", id, device);
        return service.updateDeviceById(id, device);
    }

    @PutMapping("/change-state/{id}")
    public ResponseEntity<Void> updateDeviceStateById(@PathVariable Long id, @RequestBody DeviceRequest device) {
        log.info("Request to update device state by id: {} with state details: {}", id, device.getState());
        return service.updateDeviceStateById(id, device.getState());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceResponse> getDeviceById(@PathVariable Long id) {
        log.info("Request to fetch the device: {}", id);
        return service.getDeviceById(id);
    }

    @GetMapping
    public ResponseEntity<List<DeviceResponse>> getDevices(
            @RequestParam(required = false) String brand, @RequestParam(required = false) String state) {
        log.info("Fetching devices with filters: brand={}, state={}", brand, state);
        return service.getDevices(brand, state);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDeviceById(@PathVariable Long id){
        log.info("Request to delete device by id: {}", id);
        return service.deleteDeviceById(id);
    }

}
