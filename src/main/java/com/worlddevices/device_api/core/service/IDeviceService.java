package com.worlddevices.device_api.core.service;

import com.worlddevices.device_api.api.dto.request.DeviceRequest;
import com.worlddevices.device_api.api.dto.response.DeviceResponse;

public interface IDeviceService {

    DeviceResponse save(DeviceRequest device);

}
