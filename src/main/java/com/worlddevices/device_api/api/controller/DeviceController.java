package com.worlddevices.device_api.api.controller;

import com.worlddevices.device_api.api.dto.request.DeviceRequest;
import com.worlddevices.device_api.api.dto.response.DeviceResponse;
import com.worlddevices.device_api.core.service.IDeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/device")
public class DeviceController {

    private final IDeviceService service;

    public DeviceController(IDeviceService service) {
        this.service = service;
    }

    @PostMapping
    public DeviceResponse saveDevice(@RequestBody DeviceRequest device) {
        log.info("Request to save device: {}", device.getName());
        return service.save(device);
    }

}
