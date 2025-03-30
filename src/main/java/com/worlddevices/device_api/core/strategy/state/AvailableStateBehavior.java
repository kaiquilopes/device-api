package com.worlddevices.device_api.core.strategy.state;

import com.worlddevices.device_api.core.domain.DeviceEntity;
import com.worlddevices.device_api.core.enums.StateDeviceEnum;
import org.springframework.stereotype.Component;

@Component
public class AvailableStateBehavior implements StateBehaviorStrategy {

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
