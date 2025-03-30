package com.worlddevices.device_api.api.dto.request;

import com.worlddevices.device_api.core.enums.StateDeviceEnum;
import io.swagger.v3.oas.annotations.media.Schema;

public record DeviceStateUpdateRequest(
        @Schema(description = "Device State", example = "AVAILABLE") StateDeviceEnum state) {
}
