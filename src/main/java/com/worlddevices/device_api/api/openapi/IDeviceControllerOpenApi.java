package com.worlddevices.device_api.api.openapi;

import com.worlddevices.device_api.api.dto.request.DeviceRequest;
import com.worlddevices.device_api.api.dto.response.DeviceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Device Controller", description = "Capable of persisting and managing device resources")
public interface IDeviceControllerOpenApi {

    @Operation(summary = "Create a new device", responses = {
            @ApiResponse(responseCode = "200", description = "Device saved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    ResponseEntity<DeviceResponse> saveDevice(DeviceRequest device);

    @Operation(summary = "Update device by ID",  responses = {
            @ApiResponse(responseCode = "200", description = "Device updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid device state")
    })
    ResponseEntity<DeviceResponse> updateDeviceById(
            @Parameter(description = "Device ID", required = true, in = ParameterIn.PATH, example = "10") Long id,
            DeviceRequest device);

    @Operation(summary = "Update device state",  responses = {
            @ApiResponse(responseCode = "200", description = "Device state update successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid device state")
    })
    ResponseEntity<Void> updateDeviceStateById(
            @Parameter(description = "Device ID", required = true, in = ParameterIn.PATH, example = "10") Long id,
            DeviceRequest device);

    @Operation(summary = "Fetch a single device by ID",  responses = {
            @ApiResponse(responseCode = "200", description = "Device fetched successfully"),
            @ApiResponse(responseCode = "204", description = "Device not found")
    })
    ResponseEntity<DeviceResponse> getDeviceById(
            @Parameter(description = "Device ID", required = true, in = ParameterIn.PATH, example = "10") Long id);

    @Operation(summary = "Fetch devices by brand and/or state or all devices",  responses = {
            @ApiResponse(responseCode = "200", description = "Device fetched successfully"),
            @ApiResponse(responseCode = "204", description = "Device not found")
    })
    ResponseEntity<List<DeviceResponse>> getDevices(
            @Parameter(description = "Device Brand", in = ParameterIn.QUERY, example = "Samsung") String brand,
            @Parameter(description = "Device State", in = ParameterIn.QUERY, example = "IN_USE") String state);

    @Operation(summary = "Delete a single device",  responses = {
            @ApiResponse(responseCode = "200", description = "Device deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid device state"),
            @ApiResponse(responseCode = "204", description = "Device not found")
    })
    ResponseEntity<Void> deleteDeviceById(
            @Parameter(description = "Device ID", required = true, in = ParameterIn.PATH, example = "10") Long id);

}
