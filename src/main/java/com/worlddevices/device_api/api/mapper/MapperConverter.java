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
        configureModelMapper();
    }

    /** Method to handle record convert class */
    private void configureModelMapper() {
        mapper.createTypeMap(DeviceEntity.class, DeviceResponse.class)
                .setConverter(context -> {
                    DeviceEntity source = context.getSource();
                    return new DeviceResponse(
                            source.getId(),
                            source.getName(),
                            source.getBrand(),
                            source.getState(),
                            source.getCreationTime()
                    );
                });

        mapper.createTypeMap(DeviceRequest.class, DeviceEntity.class)
                .setConverter(context -> {
                    DeviceRequest source = context.getSource();
                    DeviceEntity destination = new DeviceEntity();
                    destination.setName(source.name());
                    destination.setBrand(source.brand());
                    destination.setState(source.state());
                    return destination;
                });
    }

    public DeviceResponse convertToDeviceResponse(DeviceEntity device) {
        return mapper.map(device, DeviceResponse.class);
    }

    public DeviceEntity convertToDeviceEntity(DeviceRequest device) {
        return mapper.map(device, DeviceEntity.class);
    }

    public List<DeviceResponse> convertAllToDeviceResponse(List<DeviceEntity> devices) {
        return devices.stream().map(this::convertToDeviceResponse).collect(Collectors.toList());
    }

}
