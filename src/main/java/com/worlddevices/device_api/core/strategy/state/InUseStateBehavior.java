package com.worlddevices.device_api.core.strategy.state;

import com.worlddevices.device_api.core.domain.DeviceEntity;
import com.worlddevices.device_api.core.enums.StateDeviceEnum;
import org.springframework.stereotype.Component;

@Component
public class InUseStateBehavior implements StateBehaviorStrategy{

    @Override
    public boolean canUpdate(DeviceEntity device) {
        return false;
    }

    @Override
    public boolean canDelete(DeviceEntity device) {
        return false;
    }

    @Override
    public DeviceEntity handleUpdate(DeviceEntity device, String newName, String newBrand, StateDeviceEnum newState) {
        throw new IllegalStateException("Device in use cannot be updated.");
    }
}
