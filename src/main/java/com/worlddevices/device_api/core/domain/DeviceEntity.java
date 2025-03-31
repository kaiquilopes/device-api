package com.worlddevices.device_api.core.domain;

import com.worlddevices.device_api.core.enums.StateDeviceEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entity class representing a device.
 */

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "device")
public class DeviceEntity {

    /**
     * The unique identifier for a device.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", columnDefinition = "ENUM('AVAILABLE','IN_USE','INACTIVE')", nullable = false)
    private StateDeviceEnum state;

    @Column(name = "creation_time", nullable = false, updatable = false, insertable = false)
    private LocalDateTime creationTime = LocalDateTime.now();

}
