package com.worlddevices.device_api.core.strategy.state;

import com.worlddevices.device_api.core.domain.DeviceEntity;
import com.worlddevices.device_api.core.enums.StateDeviceEnum;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InactiveStateBehaviorTest {

    @InjectMocks
    private InactiveStateBehavior inactiveStateBehavior;

    @Test
    void canUpdateTest() {
        assertFalse(inactiveStateBehavior.canUpdate(new DeviceEntity()));
    }

    @Test
    void canDeleteTest() {
        assertTrue(inactiveStateBehavior.canDelete(new DeviceEntity()));
    }

    @Test
    void handleUpdateTest(){
        assertThrows(IllegalStateException.class, () ->
                inactiveStateBehavior.handleUpdate(new DeviceEntity(), "New Name", "New Brand",
                        StateDeviceEnum.AVAILABLE));
    }
}
