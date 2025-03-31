package com.worlddevices.device_api.api.controller;

import com.worlddevices.device_api.api.dto.request.DeviceRequest;
import com.worlddevices.device_api.api.dto.request.DeviceStateUpdateRequest;
import com.worlddevices.device_api.api.dto.response.DeviceResponse;
import com.worlddevices.device_api.core.enums.StateDeviceEnum;
import com.worlddevices.device_api.core.service.IDeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class DeviceControllerTest {

    @InjectMocks
    private DeviceController controller;

    @Mock
    private IDeviceService service;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void saveDeviceTest() throws Exception {

        when(service.save(any(DeviceRequest.class))).thenReturn(ResponseEntity.ok(buildDeviceResponse()));

        mockMvc.perform(post("/devices")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"S21\", \"brand\": \"Samsung\", \"state\": \"AVAILABLE\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("S21"))
                .andExpect(jsonPath("$.brand").value("Samsung"))
                .andExpect(jsonPath("$.state").value("IN_USE"));
    }

    @Test
    void updateDeviceByIdTest() throws Exception {
        Long id = 1L;

        when(service.updateDeviceById(anyLong(),  any(DeviceRequest.class)))
                .thenReturn(ResponseEntity.ok(buildDeviceResponse()));

        mockMvc.perform(put("/devices/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"S21\", \"brand\": \"Samsung\", \"state\": \"AVAILABLE\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("S21"))
                .andExpect(jsonPath("$.brand").value("Samsung"))
                .andExpect(jsonPath("$.state").value("IN_USE"));
    }

    @Test
    void updateDeviceStateByIdTest() throws Exception {
        Long id = 1L;

        when(service.updateDeviceStateById(anyLong(), any(DeviceStateUpdateRequest.class)))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(put("/devices/change-state/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"state\": \"AVAILABLE\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void getDeviceByIdTest() throws Exception {
        Long id = 1L;

        when(service.getDeviceById(anyLong())).thenReturn(ResponseEntity.ok(buildDeviceResponse()));

        mockMvc.perform(get("/devices/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("S21"))
                .andExpect(jsonPath("$.brand").value("Samsung"))
                .andExpect(jsonPath("$.state").value("IN_USE"));
    }

    @Test
    void getDevicesTest() throws Exception {

        List<DeviceResponse> devices = new ArrayList<>();
        DeviceResponse device = new DeviceResponse(1L, "S21", "Samsung", StateDeviceEnum.IN_USE, LocalDateTime.now());
        devices.add(device);
        when(service.getDevices(anyString(), anyString())).thenReturn(ResponseEntity.ok(devices));

        mockMvc.perform(get("/devices")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteDeviceByIdTest() throws Exception {
        Long id = 1L;

        when(service.deleteDeviceById(anyLong())).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(delete("/devices/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private DeviceResponse buildDeviceResponse(){
        return new DeviceResponse(1L, "S21", "Samsung", StateDeviceEnum.IN_USE, LocalDateTime.now());
    }

}
