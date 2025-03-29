package com.worlddevices.device_api.core.service.impl;

import com.worlddevices.device_api.api.dto.request.DeviceRequest;
import com.worlddevices.device_api.api.dto.response.DeviceResponse;
import com.worlddevices.device_api.api.mapper.MapperConverter;
import com.worlddevices.device_api.core.domain.DeviceEntity;
import com.worlddevices.device_api.core.enums.StateDeviceEnum;
import com.worlddevices.device_api.core.repository.DeviceRepository;
import com.worlddevices.device_api.core.service.IDeviceService;
import com.worlddevices.device_api.core.strategy.state.StateBehaviorContext;
import com.worlddevices.device_api.core.strategy.state.StateBehaviorStrategy;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
    public DeviceResponse save(DeviceRequest device) {
        log.info("Saving device: {}", device.getName());
        DeviceEntity entity = mapper.getRequestDeviceEntity(device);
        return mapper.getDeviceModel(repository.save(entity));
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
        return ResponseEntity.ok(mapper.getDeviceModel(updatedDevice));
    }

    @Override
    public ResponseEntity updateDeviceStateById(Long id, StateDeviceEnum state) {
        log.info("Updating device ID: {} to state {}", id, state);
        Optional<DeviceEntity> deviceEntity = repository.findById(id);

        deviceEntity.ifPresentOrElse(device -> {
            device.setState(state);
            repository.save(device);
        }, () -> ResponseEntity.noContent());

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<DeviceResponse> getDeviceById(Long id) {
        log.info("Fetching device with ID: {}", id);
        Optional<DeviceEntity> deviceEntity = repository.findById(id);
        if (deviceEntity.isPresent()) {
            return ResponseEntity.ok(mapper.getDeviceModel(deviceEntity.get()));
        }
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<DeviceResponse>> getDevices(String brand, String state) {
        List<DeviceEntity> devices;

        if (brand != null && state != null) {
            log.info("Request to fetch devices by brand: {} and state: {}", brand, state);
            devices = repository.findByBrandAndState(brand, StateDeviceEnum.valueOf(state));
        } else if (brand != null) {
            log.info("Request to fetch the device by brand: {}", brand);
            devices = repository.findByBrand(brand);
        } else if (state != null) {
            log.info("Request to fetch the device by state: {}", state);
            devices = repository.findByState(StateDeviceEnum.valueOf(state));
        } else {
            log.info("Request to fetch all devices");
            devices = repository.findAll();
        }
        if(!devices.isEmpty()){
            return ResponseEntity.ok(mapper.getListDevicesModel(devices));
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
