package com.worlddevices.device_api.api.dto.response;

import com.worlddevices.device_api.core.enums.StateDeviceEnum;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record DeviceResponse(
        @Schema(description = "ID of the device", example = "11") Long id,
        @Schema(description = "The name of the device", example = "iPhone 16 PRO MAX") String name,
        @Schema(description = "Brand of the device", example = "Apple") String brand,
        @Schema(description = "Device State", example = "AVAILABLE") StateDeviceEnum state,
        @Schema(description = "Device creation date", example = "2025-03-30T13:54:40.592Z") LocalDateTime creationTime
) {
}
