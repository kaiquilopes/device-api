package com.worlddevices.device_api.core.service.impl;

import com.worlddevices.device_api.api.dto.request.DeviceRequest;
import com.worlddevices.device_api.api.dto.response.DeviceResponse;
import com.worlddevices.device_api.core.domain.DeviceEntity;
import com.worlddevices.device_api.core.repository.DeviceRepository;
import com.worlddevices.device_api.core.service.IDeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeviceService implements IDeviceService {

    private final DeviceRepository repository;

    public DeviceService(DeviceRepository repository) {
        this.repository = repository;
    }

    @Override
    public DeviceResponse save(DeviceRequest device) {
        //TODO implement model mapper to convert
        DeviceEntity entity = new DeviceEntity();
        entity.setName(device.getName());
        entity.setBrand(device.getBrand());
        entity.setState(device.getState());
        entity.setCreationTime(device.getCreationDate());
        repository.save(entity);
        return new DeviceResponse(entity.getId(), entity.getName(), entity.getBrand(), entity.getState(), entity.getCreationTime());
    }
}
