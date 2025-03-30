package com.worlddevices.device_api.core.exception;

public class InvalidDeviceStateException extends RuntimeException {

    public InvalidDeviceStateException(String invalidState) {
        super(String.format("Invalid device state: %s. Valid states are: AVAILABLE, IN_USE, INACTIVE", invalidState));
    }

}
