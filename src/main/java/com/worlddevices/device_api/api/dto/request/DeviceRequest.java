package com.worlddevices.device_api.api.dto.request;

import com.worlddevices.device_api.core.enums.StateDeviceEnum;
import io.swagger.v3.oas.annotations.media.Schema;

public record DeviceRequest(
        @Schema(description = "The name of the device", example = "iPhone 16 PRO MAX") String name,
        @Schema(description = "Brand of the device", example = "Apple") String brand,
        @Schema(description = "Device State", example = "AVAILABLE") StateDeviceEnum state
) {
}
