package com.worlddevices.device_api.core.service;

import com.worlddevices.device_api.api.dto.request.DeviceRequest;
import com.worlddevices.device_api.api.dto.request.DeviceStateUpdateRequest;
import com.worlddevices.device_api.api.dto.response.DeviceResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IDeviceService {

    ResponseEntity<DeviceResponse> save(DeviceRequest device);

    ResponseEntity<DeviceResponse> updateDeviceById(Long id, DeviceRequest device);

    ResponseEntity updateDeviceStateById(Long id, DeviceStateUpdateRequest stateUpdateRequest);

    ResponseEntity<DeviceResponse> getDeviceById(Long id);

    ResponseEntity<List<DeviceResponse>> getDevices(String brand, String state);

    ResponseEntity deleteDeviceById(Long id);

}
