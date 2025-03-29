package com.worlddevices.device_api.core.service;

import com.worlddevices.device_api.api.dto.request.DeviceRequest;
import com.worlddevices.device_api.api.dto.response.DeviceResponse;
import com.worlddevices.device_api.core.enums.StateDeviceEnum;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IDeviceService {

    DeviceResponse save(DeviceRequest device);

    ResponseEntity<DeviceResponse> updateDeviceById(Long id, DeviceRequest device);

    ResponseEntity<DeviceResponse> updateDeviceStateById(Long id, StateDeviceEnum state);

    ResponseEntity<DeviceResponse> getDeviceById(Long id);

    ResponseEntity<List<DeviceResponse>> getDevices(String brand, String state);

    ResponseEntity deleteDeviceById(Long id);

}
