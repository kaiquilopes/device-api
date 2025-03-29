package com.worlddevices.device_api.core.repository;

import com.worlddevices.device_api.core.domain.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {
}
