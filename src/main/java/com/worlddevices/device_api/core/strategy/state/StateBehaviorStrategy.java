package com.worlddevices.device_api.core.strategy.state;

import com.worlddevices.device_api.core.domain.DeviceEntity;
import com.worlddevices.device_api.core.enums.StateDeviceEnum;

public interface StateBehaviorStrategy {

    boolean canUpdate(DeviceEntity device);

    boolean canDelete(DeviceEntity device);

    DeviceEntity handleUpdate(DeviceEntity device, String newName, String newBrand, StateDeviceEnum newState);

}
