package com.worlddevices.device_api.api.mapper;

import com.worlddevices.device_api.api.dto.request.DeviceRequest;
import com.worlddevices.device_api.api.dto.response.DeviceResponse;
import com.worlddevices.device_api.core.domain.DeviceEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * MapperConverter is responsible for converting between different data transfer objects (DTOs) and entities.
 */
@Component
public class MapperConverter {

    private final ModelMapper mapper;

    public MapperConverter(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public DeviceResponse getDeviceModel(DeviceEntity device){
        return mapper.map(device, DeviceResponse.class);
    }

    public DeviceEntity getDeviceEntity(DeviceResponse device){
        return mapper.map(device, DeviceEntity.class);
    }

    public DeviceEntity getRequestDeviceEntity(DeviceRequest device){
        return mapper.map(device, DeviceEntity.class);
    }

    public List<DeviceResponse> getListDevicesModel(List<DeviceEntity> devices){
        return devices.stream().map(this::getDeviceModel).collect(Collectors.toList());
    }

    public List<DeviceEntity> getListDevicesEntity(List<DeviceResponse> devices){
        return devices.stream().map(this::getDeviceEntity).collect(Collectors.toList());
    }
}
