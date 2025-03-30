package com.worlddevices.device_api.api.controller;

import com.worlddevices.device_api.api.dto.request.DeviceRequest;
import com.worlddevices.device_api.api.dto.request.DeviceStateUpdateRequest;
import com.worlddevices.device_api.api.dto.response.DeviceResponse;
import com.worlddevices.device_api.api.openapi.IDeviceControllerOpenApi;
import com.worlddevices.device_api.core.service.IDeviceService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/devices")
public class DeviceController implements IDeviceControllerOpenApi {

    private final IDeviceService service;

    public DeviceController(IDeviceService service) {
        this.service = service;
    }

    @Override
    @PostMapping
    public ResponseEntity<DeviceResponse> saveDevice(@RequestBody @Valid DeviceRequest device) {
        log.info("Request to save device: {}", device.name());
        return service.save(device);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<DeviceResponse> updateDeviceById(
            @PathVariable Long id,
            @RequestBody @Valid DeviceRequest device) {
        log.info("Request to update device by id: {} with details: {}", id, device);
        return service.updateDeviceById(id, device);
    }

    @Override
    @PutMapping("/change-state/{id}")
    public ResponseEntity<Void> updateDeviceStateById(
            @PathVariable Long id, @RequestBody @Valid DeviceStateUpdateRequest request) {
        log.info("Request to update device state by id: {} with state details: {}", id, request.state());
        return service.updateDeviceStateById(id, request.state());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<DeviceResponse> getDeviceById(@PathVariable Long id) {
        log.info("Request to fetch the device: {}", id);
        return service.getDeviceById(id);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<DeviceResponse>> getDevices(
            @RequestParam(required = false) String brand, @RequestParam(required = false) String state) {
        log.info("Fetching devices with filters: brand={}, state={}", brand, state);
        return service.getDevices(brand, state);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeviceById(@PathVariable Long id){
        log.info("Request to delete device by id: {}", id);
        return service.deleteDeviceById(id);
    }

}
