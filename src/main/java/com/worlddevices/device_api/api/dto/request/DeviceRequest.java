package com.worlddevices.device_api.api.dto.request;

import com.worlddevices.device_api.core.enums.StateDeviceEnum;
import lombok.Data;

@Data
public class DeviceRequest {

    private String name;

    private String brand;

    private StateDeviceEnum state;

}
