package com.worlddevices.device_api.core.service.impl;

import com.worlddevices.device_api.api.dto.request.DeviceRequest;
import com.worlddevices.device_api.api.dto.response.DeviceResponse;
import com.worlddevices.device_api.api.mapper.MapperConverter;
import com.worlddevices.device_api.core.domain.DeviceEntity;
import com.worlddevices.device_api.core.enums.StateDeviceEnum;
import com.worlddevices.device_api.core.exception.InvalidDeviceStateException;
import com.worlddevices.device_api.core.repository.DeviceRepository;
import com.worlddevices.device_api.core.service.IDeviceService;
import com.worlddevices.device_api.core.strategy.state.StateBehaviorContext;
import com.worlddevices.device_api.core.strategy.state.StateBehaviorStrategy;
import com.worlddevices.device_api.core.validation.DeviceStateValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DeviceService implements IDeviceService {

    private final DeviceRepository repository;

    private final MapperConverter mapper;

    private final StateBehaviorContext stateBehaviorContext;

    public DeviceService(
            DeviceRepository repository, MapperConverter mapper, StateBehaviorContext stateBehaviorContext) {
        this.repository = repository;
        this.mapper = mapper;
        this.stateBehaviorContext = stateBehaviorContext;
    }

    @Override
    public ResponseEntity<DeviceResponse> save(DeviceRequest device) {
        log.info("Saving device: {}", device.getName());
        DeviceEntity entity = mapper.convertToDeviceEntity(device);
        return ResponseEntity.ok(mapper.convertToDeviceResponse(repository.save(entity)));
    }

    @Override
    public ResponseEntity<DeviceResponse> updateDeviceById(Long id, DeviceRequest device) {
        log.info("Updating device with ID: {}", id);
        DeviceEntity existingDevice =
                repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Device not found"));

        StateBehaviorStrategy strategy = stateBehaviorContext.getStrategy(existingDevice.getState());
        if (!strategy.canUpdate(existingDevice)) {
            throw new IllegalStateException("Update not allowed for state: " + existingDevice.getState());
        }

        DeviceEntity updatedDevice = strategy.handleUpdate(
                existingDevice, device.getName(), device.getBrand(), device.getState());
        repository.save(updatedDevice);
        return ResponseEntity.ok(mapper.convertToDeviceResponse(updatedDevice));
    }

    @Override
    public ResponseEntity<Void> updateDeviceStateById(Long id, StateDeviceEnum state) {
        log.info("Updating device ID: {} to state {}", id, state);
        Optional<DeviceEntity> deviceOptional = repository.findById(id);

        if(deviceOptional.isEmpty()){
            log.warn("Device with ID {} not found for state update", id);
            return ResponseEntity.noContent().build();
        }

        DeviceEntity device = deviceOptional.get();
        device.setState(state);
        repository.save(device);

        log.info("Updated state of device ID {} to {}", id, state);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<DeviceResponse> getDeviceById(Long id) {
        log.info("Fetching device with ID: {}", id);
        Optional<DeviceEntity> deviceEntity = repository.findById(id);
        if (deviceEntity.isPresent()) {
            return ResponseEntity.ok(mapper.convertToDeviceResponse(deviceEntity.get()));
        }
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<DeviceResponse>> getDevices(String brand, String state) {
        List<DeviceEntity> devices;

        try{
            if (brand != null && state != null) {
                StateDeviceEnum validatedState = DeviceStateValidator.validate(state);
                log.info("Request to fetch devices by brand: {} and state: {}", brand, validatedState);
                devices = repository.findByBrandAndState(brand, validatedState);
            } else if (brand != null) {
                log.info("Request to fetch the device by brand: {}", brand);
                devices = repository.findByBrand(brand);
            } else if (state != null) {
                StateDeviceEnum validatedState = DeviceStateValidator.validate(state);
                log.info("Request to fetch the device by state: {}", validatedState);
                devices = repository.findByState(validatedState);
            } else {
                log.info("Request to fetch all devices");
                devices = repository.findAll();
            }
            if(!devices.isEmpty()){
                return ResponseEntity.ok(mapper.convertAllToDeviceResponse(devices));
            }
        } catch (InvalidDeviceStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteDeviceById(Long id) {
        log.info("Deleting device with ID: {}", id);
        DeviceEntity device =
                repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Device not found"));

        StateBehaviorStrategy strategy = stateBehaviorContext.getStrategy(device.getState());
        if (!strategy.canDelete(device)) {
            throw new IllegalStateException("Deletion not allowed for state: " + device.getState());
        }

        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
