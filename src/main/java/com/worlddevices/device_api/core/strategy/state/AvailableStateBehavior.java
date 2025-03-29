package com.worlddevices.device_api.core.strategy.state;

import com.worlddevices.device_api.api.mapper.MapperConverter;
import com.worlddevices.device_api.core.domain.DeviceEntity;
import com.worlddevices.device_api.core.enums.StateDeviceEnum;
import org.springframework.stereotype.Component;

@Component
public class AvailableStateBehavior implements StateBehaviorStrategy {

    private final MapperConverter mapper;

    public AvailableStateBehavior(MapperConverter mapper){
        this.mapper = mapper;
    }

    @Override
    public boolean canUpdate(DeviceEntity device) {
        return true;
    }

    @Override
    public boolean canDelete(DeviceEntity device) {
        return false;
    }

    @Override
    public DeviceEntity handleUpdate(DeviceEntity device, String newName, String newBrand, StateDeviceEnum newState) {
        device.setName(newName);
        device.setBrand(newBrand);
        device.setState(newState);
        return device;
    }
}
