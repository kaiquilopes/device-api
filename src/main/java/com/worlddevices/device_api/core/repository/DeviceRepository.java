package com.worlddevices.device_api.core.repository;

import com.worlddevices.device_api.core.domain.DeviceEntity;
import com.worlddevices.device_api.core.enums.StateDeviceEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {

    List<DeviceEntity> findByBrand(String brand);

    List<DeviceEntity> findByState(StateDeviceEnum state);

    List<DeviceEntity> findByBrandAndState(String brand, StateDeviceEnum state);

}
