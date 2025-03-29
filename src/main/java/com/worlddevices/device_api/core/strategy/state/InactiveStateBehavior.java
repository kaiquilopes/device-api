package com.worlddevices.device_api.core.strategy.state;

import com.worlddevices.device_api.core.domain.DeviceEntity;
import com.worlddevices.device_api.core.enums.StateDeviceEnum;
import org.springframework.stereotype.Component;

@Component
public class InactiveStateBehavior implements StateBehaviorStrategy {

    @Override
    public boolean canUpdate(DeviceEntity device) {
        return false;
    }

    @Override
    public boolean canDelete(DeviceEntity device) {
        return true;
    }

    @Override
    public DeviceEntity handleUpdate(DeviceEntity device, String newName, String newBrand, StateDeviceEnum newState) {
        throw new IllegalStateException("Inactive devices cannot be updated.");
    }
}
