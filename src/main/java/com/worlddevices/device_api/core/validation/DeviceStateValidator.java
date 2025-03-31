package com.worlddevices.device_api.core.validation;

import com.worlddevices.device_api.core.enums.StateDeviceEnum;
import com.worlddevices.device_api.core.exception.InvalidDeviceStateException;

public final class DeviceStateValidator {

    public static StateDeviceEnum validate(String state) {
        try {
            return StateDeviceEnum.valueOf(state);
        } catch (IllegalArgumentException e) {
            throw new InvalidDeviceStateException(state);
        }
    }

}
