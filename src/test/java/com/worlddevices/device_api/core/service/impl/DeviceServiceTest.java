package com.worlddevices.device_api.core.service.impl;

import com.worlddevices.device_api.api.dto.request.DeviceRequest;
import com.worlddevices.device_api.api.dto.request.DeviceStateUpdateRequest;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
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

        ResponseEntity<List<DeviceResponse>> response = service.getDevices(null, null);
        assertNotNull(response);

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
        List<DeviceEntity> deviceEntity = buildListDeviceEntity();
        when(repository.findAll()).thenReturn(deviceEntity);

        List<DeviceResponse> devicesResponse = new ArrayList<>();
        DeviceResponse deviceResponse = new DeviceResponse(
                1L, "iPhone 15", "Apple", StateDeviceEnum.AVAILABLE, LocalDateTime.now());
        devicesResponse.add(deviceResponse);
        when(mapper.convertAllToDeviceResponse(anyList())).thenReturn(devicesResponse);

        ResponseEntity<List<DeviceResponse>> response = service.getDevices(null, null);
        assertNotNull(response);
        assertNotNull(response.getBody());

        DeviceResponse listActualResponse = response.getBody().get(0);
        assertEquals(deviceEntity.get(0).getId(), listActualResponse.id());
        assertEquals(deviceEntity.get(0).getName(), listActualResponse.name());
        assertEquals(deviceEntity.get(0).getBrand(), listActualResponse.brand());
        assertEquals(deviceEntity.get(0).getState(), listActualResponse.state());

        verify(repository, never()).findByBrandAndState(anyString(), any(StateDeviceEnum.class));
        verify(repository, never()).findByBrand(anyString());
        verify(repository, never()).findByState(any(StateDeviceEnum.class));
        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).convertAllToDeviceResponse(anyList());
    }

    @Test
    void getDevicesStateTest() {

        List<DeviceEntity> deviceEntity = buildListDeviceEntity();
        when(repository.findByState(any(StateDeviceEnum.class))).thenReturn(deviceEntity);

        List<DeviceResponse> devicesResponse = new ArrayList<>();
        DeviceResponse deviceResponse = new DeviceResponse(
                1L, "iPhone 15", "Apple", StateDeviceEnum.AVAILABLE, LocalDateTime.now());
        devicesResponse.add(deviceResponse);
        when(mapper.convertAllToDeviceResponse(anyList())).thenReturn(devicesResponse);

        ResponseEntity<List<DeviceResponse>> response = service.getDevices(null, "AVAILABLE");
        assertNotNull(response);
        assertNotNull(response.getBody());

        DeviceResponse listActualResponse = response.getBody().get(0);
        assertEquals(deviceEntity.get(0).getId(), listActualResponse.id());
        assertEquals(deviceEntity.get(0).getName(), listActualResponse.name());
        assertEquals(deviceEntity.get(0).getBrand(), listActualResponse.brand());
        assertEquals(deviceEntity.get(0).getState(), listActualResponse.state());

        verify(repository, never()).findByBrandAndState(anyString(), any(StateDeviceEnum.class));
        verify(repository, never()).findByBrand(anyString());
        verify(repository, times(1)).findByState(any(StateDeviceEnum.class));
        verify(repository, never()).findAll();
        verify(mapper, times(1)).convertAllToDeviceResponse(anyList());
    }

    @Test
    void getDevicesBrandTest() {

        List<DeviceEntity> deviceEntity = buildListDeviceEntity();
        when(repository.findByBrand(anyString())).thenReturn(deviceEntity);

        List<DeviceResponse> devicesResponse = new ArrayList<>();
        DeviceResponse deviceResponse = new DeviceResponse(
                1L, "iPhone 15", "Apple", StateDeviceEnum.AVAILABLE, LocalDateTime.now());
        devicesResponse.add(deviceResponse);
        when(mapper.convertAllToDeviceResponse(anyList())).thenReturn(devicesResponse);

        ResponseEntity<List<DeviceResponse>> response = service.getDevices("Xiaomi", null);
        assertNotNull(response);
        assertNotNull(response.getBody());

        DeviceResponse listActualResponse = response.getBody().get(0);
        assertEquals(deviceEntity.get(0).getId(), listActualResponse.id());
        assertEquals(deviceEntity.get(0).getName(), listActualResponse.name());
        assertEquals(deviceEntity.get(0).getBrand(), listActualResponse.brand());
        assertEquals(deviceEntity.get(0).getState(), listActualResponse.state());

        verify(repository, never()).findByBrandAndState(anyString(), any(StateDeviceEnum.class));
        verify(repository, times(1)).findByBrand(anyString());
        verify(repository, never()).findByState(any(StateDeviceEnum.class));
        verify(repository, never()).findAll();
        verify(mapper, times(1)).convertAllToDeviceResponse(anyList());
    }

    @Test
    void getDevicesBrandAndStateTest() {

        List<DeviceEntity> deviceEntity = buildListDeviceEntity();
        deviceEntity.get(0).setState(StateDeviceEnum.IN_USE);
        when(repository.findByBrandAndState(anyString(), any(StateDeviceEnum.class)))
                .thenReturn(deviceEntity);

        List<DeviceResponse> devicesResponse = new ArrayList<>();
        DeviceResponse deviceResponse = new DeviceResponse(
                1L, "iPhone 15", "Apple", StateDeviceEnum.IN_USE, LocalDateTime.now());
        devicesResponse.add(deviceResponse);
        when(mapper.convertAllToDeviceResponse(anyList())).thenReturn(devicesResponse);

        ResponseEntity<List<DeviceResponse>> response = service.getDevices("Xiaomi", "IN_USE");
        assertNotNull(response);
        assertNotNull(response.getBody());

        DeviceResponse listActualResponse = response.getBody().get(0);
        assertEquals(deviceEntity.get(0).getId(), listActualResponse.id());
        assertEquals(deviceEntity.get(0).getName(), listActualResponse.name());
        assertEquals(deviceEntity.get(0).getBrand(), listActualResponse.brand());
        assertEquals(deviceEntity.get(0).getState(), listActualResponse.state());

        verify(repository, times(1)).findByBrandAndState(anyString(), any(StateDeviceEnum.class));
        verify(repository, never()).findByBrand(anyString());
        verify(repository, never()).findByState(any(StateDeviceEnum.class));
        verify(repository, never()).findAll();
        verify(mapper, times(1)).convertAllToDeviceResponse(anyList());
    }

    @Test
    void getDeviceByIdTest(){
        DeviceEntity deviceEntity = buildDeviceEntity(StateDeviceEnum.IN_USE);
        when(repository.findById(anyLong())).thenReturn(Optional.of(deviceEntity));

        DeviceResponse deviceResponse = new DeviceResponse(
                1L, "iPhone 15", "Apple", StateDeviceEnum.IN_USE, LocalDateTime.now());
        when(mapper.convertToDeviceResponse(any(DeviceEntity.class))).thenReturn(deviceResponse);

        ResponseEntity<DeviceResponse> response = service.getDeviceById(1L);
        assertNotNull(response);
        assertNotNull(response.getBody());

        DeviceResponse actualResponse = response.getBody();
        assertEquals(deviceEntity.getId(), actualResponse.id());
        assertEquals(deviceEntity.getName(), actualResponse.name());
        assertEquals(deviceEntity.getBrand(), actualResponse.brand());
        assertEquals(deviceEntity.getState(), actualResponse.state());

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

        service.updateDeviceStateById(1L, new DeviceStateUpdateRequest(StateDeviceEnum.AVAILABLE));
        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).save(any(DeviceEntity.class));
    }

    @Test
    void updateDeviceStateByIdDeviceNotFoundTest() {

        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                service.updateDeviceStateById(1L, new DeviceStateUpdateRequest(StateDeviceEnum.IN_USE)));
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

        DeviceEntity deviceEntity = buildDeviceEntity(StateDeviceEnum.AVAILABLE);
        when(repository.findById(anyLong())).thenReturn(Optional.of(deviceEntity));

        when(stateBehaviorContext.getStrategy(any(StateDeviceEnum.class)))
                .thenReturn(buildStateBehaviorStrategyAvailable(true, false));

        DeviceEntity updatedDeviceEntity = buildDeviceEntity(StateDeviceEnum.IN_USE);
        updatedDeviceEntity.setName("ThinkPad X1");
        updatedDeviceEntity.setBrand("Lenovo");
        when(repository.save(any(DeviceEntity.class))).thenReturn(updatedDeviceEntity);

        DeviceResponse deviceResponse = new DeviceResponse(
                1L, "ThinkPad X1", "Lenovo", StateDeviceEnum.AVAILABLE, LocalDateTime.now());
        when(mapper.convertToDeviceResponse(any(DeviceEntity.class))).thenReturn(deviceResponse);

        ResponseEntity<DeviceResponse> response = service.updateDeviceById(1L,
                new DeviceRequest("ThinkPad X1", "Lenovo", StateDeviceEnum.AVAILABLE));
        assertNotNull(response);
        assertNotNull(response.getBody());

        DeviceResponse actualResponse = response.getBody();
        assertEquals(deviceEntity.getState(), actualResponse.state());
        assertNotNull(actualResponse.creationTime());

        verify(repository, times(1)).findById(anyLong());
        verify(stateBehaviorContext, times(1)).getStrategy(any(StateDeviceEnum.class));
        verify(repository, times(1)).save(any(DeviceEntity.class));
        verify(mapper, times(1)).convertToDeviceResponse(any(DeviceEntity.class));
    }

    @Test
    void saveTest() {
        when(mapper.convertToDeviceEntity(any(DeviceRequest.class))).thenReturn(new DeviceEntity());

        DeviceResponse deviceResponse = buildDeviceResponse();
        when(mapper.convertToDeviceResponse(any(DeviceEntity.class))).thenReturn(deviceResponse);
        when(repository.save(any(DeviceEntity.class))).thenReturn(buildDeviceEntity(StateDeviceEnum.AVAILABLE));

        ResponseEntity<DeviceResponse> response =
                service.save(new DeviceRequest("ThinkPad X1", "Lenovo", StateDeviceEnum.IN_USE));
        assertNotNull(response);
        assertNotNull(response.getBody());

        DeviceResponse actualResponse = response.getBody();
        assertEquals(deviceResponse.brand(), actualResponse.brand());
        assertNotNull(actualResponse.creationTime());

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
