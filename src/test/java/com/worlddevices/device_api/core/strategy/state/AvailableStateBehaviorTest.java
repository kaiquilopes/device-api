package com.worlddevices.device_api.core.strategy.state;

import com.worlddevices.device_api.core.domain.DeviceEntity;
import com.worlddevices.device_api.core.enums.StateDeviceEnum;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AvailableStateBehaviorTest {

    @InjectMocks
    private AvailableStateBehavior availableStateBehavior;

    @Test
    void canUpdateTest() {
        assertTrue(availableStateBehavior.canUpdate(new DeviceEntity()));
    }

    @Test
    void canDeleteTest() {
        assertFalse(availableStateBehavior.canDelete(new DeviceEntity()));
    }

    @Test
    void handleUpdateTest(){
        String newName = "Samsung apple";
        String newBrand = "Xiaomi";
        StateDeviceEnum state = StateDeviceEnum.AVAILABLE;
        DeviceEntity deviceEntity = availableStateBehavior.handleUpdate(new DeviceEntity(), newName, newBrand, state);
        assertEquals(newName, deviceEntity.getName());
        assertEquals(newBrand, deviceEntity.getBrand());
        assertEquals(state, deviceEntity.getState());
    }
}
