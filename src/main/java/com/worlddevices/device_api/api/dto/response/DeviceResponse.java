package com.worlddevices.device_api.api.dto.response;

import com.worlddevices.device_api.core.enums.StateDeviceEnum;

import java.time.LocalDateTime;

public record DeviceResponse(Long id, String name, String brand, StateDeviceEnum state, LocalDateTime creationDate) {

}
