package com.worlddevices.device_api.core.service.impl;

import com.worlddevices.device_api.api.dto.request.DeviceRequest;
import com.worlddevices.device_api.api.dto.response.DeviceResponse;
import com.worlddevices.device_api.api.mapper.MapperConverter;
import com.worlddevices.device_api.core.domain.DeviceEntity;
import com.worlddevices.device_api.core.enums.StateDeviceEnum;
import com.worlddevices.device_api.core.repository.DeviceRepository;
import com.worlddevices.device_api.core.strategy.state.StateBehaviorContext;
import com.worlddevices.device_api.core.strategy.state.StateBehaviorStrategy;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class DeviceServiceTest {

    @InjectMocks
    private DeviceService service;

    @Mock
    private DeviceRepository repository;

    @Mock
    private MapperConverter mapper;

    @Mock
    private StateBehaviorContext stateBehaviorContext;

    @Test
    void deleteDeviceByIdTest() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(buildDeviceEntity(StateDeviceEnum.IN_USE)));
        when(stateBehaviorContext.getStrategy(any(StateDeviceEnum.class)))
                .thenReturn(buildStateBehaviorStrategyAvailable(false, true));

        service.deleteDeviceById(1L);
        verify(repository, times(1)).findById(anyLong());
        verify(stateBehaviorContext, times(1)).getStrategy(any(StateDeviceEnum.class));
        verify(repository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteDeviceByIdCanNotDeleteTest() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(buildDeviceEntity(StateDeviceEnum.IN_USE)));
        when(stateBehaviorContext.getStrategy(any(StateDeviceEnum.class)))
                .thenReturn(buildStateBehaviorStrategyAvailable(false, false));

        assertThrows(IllegalStateException.class, () -> service.deleteDeviceById(1L));
        verify(repository, times(1)).findById(anyLong());
        verify(stateBehaviorContext, times(1)).getStrategy(any(StateDeviceEnum.class));
        verify(repository, never()).deleteById(anyLong());
    }

    @Test
    void deleteDeviceByIdDeviceNotFoundTest() {

        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.deleteDeviceById(1L));
        verify(repository, times(1)).findById(anyLong());
        verify(stateBehaviorContext, never()).getStrategy(any(StateDeviceEnum.class));
        verify(repository, never()).deleteById(anyLong());
    }

    @Test
    void getDevicesEmptyTest() {

        when(repository.findAll()).thenReturn(Collections.emptyList());

        service.getDevices(null, null);
        verify(repository, never()).findByBrandAndState(anyString(), any(StateDeviceEnum.class));
        verify(repository, never()).findByBrand(anyString());
        verify(repository, never()).findByState(any(StateDeviceEnum.class));
        verify(repository, times(1)).findAll();
        verify(mapper, never()).convertAllToDeviceResponse(anyList());
    }

    @Test
    void getDevicesInvalidStateTest() {

        when(repository.findByState(any(StateDeviceEnum.class))).thenReturn(buildListDeviceEntity());

        assertThrows(ResponseStatusException.class, () -> service.getDevices(null, "UNAVAILABLE"));
        verify(repository, never()).findByBrandAndState(anyString(), any(StateDeviceEnum.class));
        verify(repository, never()).findByBrand(anyString());
        verify(repository, never()).findByState(any(StateDeviceEnum.class));
        verify(repository, never()).findAll();
        verify(mapper, never()).convertAllToDeviceResponse(anyList());
    }

    @Test
    void getDevicesTest() {

        when(repository.findAll()).thenReturn(buildListDeviceEntity());

        service.getDevices(null, null);
        verify(repository, never()).findByBrandAndState(anyString(), any(StateDeviceEnum.class));
        verify(repository, never()).findByBrand(anyString());
        verify(repository, never()).findByState(any(StateDeviceEnum.class));
        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).convertAllToDeviceResponse(anyList());
    }

    @Test
    void getDevicesStateTest() {

        when(repository.findByState(any(StateDeviceEnum.class))).thenReturn(buildListDeviceEntity());

        service.getDevices(null, "AVAILABLE");
        verify(repository, never()).findByBrandAndState(anyString(), any(StateDeviceEnum.class));
        verify(repository, never()).findByBrand(anyString());
        verify(repository, times(1)).findByState(any(StateDeviceEnum.class));
        verify(repository, never()).findAll();
        verify(mapper, times(1)).convertAllToDeviceResponse(anyList());
    }

    @Test
    void getDevicesBrandTest() {

        when(repository.findByBrand(anyString())).thenReturn(buildListDeviceEntity());

        service.getDevices("Xiaomi", null);
        verify(repository, never()).findByBrandAndState(anyString(), any(StateDeviceEnum.class));
        verify(repository, times(1)).findByBrand(anyString());
        verify(repository, never()).findByState(any(StateDeviceEnum.class));
        verify(repository, never()).findAll();
        verify(mapper, times(1)).convertAllToDeviceResponse(anyList());
    }

    @Test
    void getDevicesBrandAndStateTest() {

        when(repository.findByBrandAndState(anyString(), any(StateDeviceEnum.class)))
                .thenReturn(buildListDeviceEntity());

        service.getDevices("Xiaomi", "IN_USE");
        verify(repository, times(1)).findByBrandAndState(anyString(), any(StateDeviceEnum.class));
        verify(repository, never()).findByBrand(anyString());
        verify(repository, never()).findByState(any(StateDeviceEnum.class));
        verify(repository, never()).findAll();
        verify(mapper, times(1)).convertAllToDeviceResponse(anyList());
    }

    @Test
    void getDeviceByIdTest(){
        when(repository.findById(anyLong())).thenReturn(Optional.of(buildDeviceEntity(StateDeviceEnum.IN_USE)));

        service.getDeviceById(1L);
        verify(repository, times(1)).findById(anyLong());
        verify(mapper, times(1)).convertToDeviceResponse(any(DeviceEntity.class));
    }

    @Test
    void getDeviceByIdDeviceNotFoundTest(){
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.getDeviceById(1L));
        verify(repository, times(1)).findById(anyLong());
        verify(mapper, never()).convertToDeviceResponse(any(DeviceEntity.class));
    }

    @Test
    void updateDeviceStateByIdTest() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(buildDeviceEntity(StateDeviceEnum.INACTIVE)));

        service.updateDeviceStateById(1L, StateDeviceEnum.AVAILABLE);
        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).save(any(DeviceEntity.class));
    }

    @Test
    void updateDeviceStateByIdDeviceNotFoundTest() {

        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.updateDeviceStateById(1L, StateDeviceEnum.IN_USE));
        verify(repository, times(1)).findById(anyLong());
        verify(repository, never()).save(any(DeviceEntity.class));
    }

    @Test
    void updateDeviceByIdTest() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(buildDeviceEntity(StateDeviceEnum.IN_USE)));
        when(stateBehaviorContext.getStrategy(any(StateDeviceEnum.class)))
                .thenReturn(buildStateBehaviorStrategyAvailable(false, false));

        assertThrows(IllegalStateException.class, () -> service.updateDeviceById(1L,
                new DeviceRequest("ThinkPad X1", "Lenovo", StateDeviceEnum.IN_USE)));

        verify(repository, times(1)).findById(anyLong());
        verify(repository, never()).save(any(DeviceEntity.class));
        verify(mapper, never()).convertToDeviceResponse(any(DeviceEntity.class));
    }

    @Test
    void updateDeviceByIdCanUpdateTest() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(buildDeviceEntity(StateDeviceEnum.AVAILABLE)));
        when(stateBehaviorContext.getStrategy(any(StateDeviceEnum.class)))
                .thenReturn(buildStateBehaviorStrategyAvailable(true, false));

        service.updateDeviceById(1L, new DeviceRequest("ThinkPad X1", "Lenovo", StateDeviceEnum.IN_USE));
        verify(repository, times(1)).findById(anyLong());
        verify(mapper, times(1)).convertToDeviceResponse(any(DeviceEntity.class));
    }

    @Test
    void saveTest() {
        when(mapper.convertToDeviceEntity(any(DeviceRequest.class))).thenReturn(new DeviceEntity());
        when(mapper.convertToDeviceResponse(any(DeviceEntity.class))).thenReturn(buildDeviceResponse());
        when(repository.save(any(DeviceEntity.class))).thenReturn(buildDeviceEntity(StateDeviceEnum.AVAILABLE));

        service.save(new DeviceRequest("ThinkPad X1", "Lenovo", StateDeviceEnum.IN_USE));
        verify(mapper, times(1)).convertToDeviceEntity(any(DeviceRequest.class));
        verify(mapper, times(1)).convertToDeviceResponse(any(DeviceEntity.class));
        verify(repository, times(1)).save(any(DeviceEntity.class));
    }

    private List<DeviceEntity> buildListDeviceEntity(){
        List<DeviceEntity> devices = new ArrayList<>();

        DeviceEntity device = buildDeviceEntity(StateDeviceEnum.AVAILABLE);
        devices.add(device);
        return devices;
    }

    private DeviceEntity buildDeviceEntity(StateDeviceEnum state){
        DeviceEntity deviceEntity = new DeviceEntity();
        deviceEntity.setId(1L);
        deviceEntity.setName("iPhone 15");
        deviceEntity.setBrand("Apple");
        deviceEntity.setState(state);
        deviceEntity.setCreationTime(LocalDateTime.now());
        return deviceEntity;
    }

    private StateBehaviorStrategy buildStateBehaviorStrategyAvailable(boolean canUpdate, boolean canDelete){
        return new StateBehaviorStrategy() {
            @Override
            public boolean canUpdate(DeviceEntity device) {
                return canUpdate;
            }

            @Override
            public boolean canDelete(DeviceEntity device) {
                return canDelete;
            }

            @Override
            public DeviceEntity handleUpdate(
                    DeviceEntity device, String newName, String newBrand, StateDeviceEnum newState) {
                return buildDeviceEntity(StateDeviceEnum.INACTIVE);
            }
        };
    }

    private DeviceResponse buildDeviceResponse(){
        return new DeviceResponse(10L, "Pixel 6", "Google", StateDeviceEnum.IN_USE, LocalDateTime.now());
    }

}
