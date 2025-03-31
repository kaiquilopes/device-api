package com.worlddevices.device_api.core.strategy.state;

import com.worlddevices.device_api.core.domain.DeviceEntity;
import com.worlddevices.device_api.core.enums.StateDeviceEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InUseStateBehaviorTest {

    @InjectMocks
    private InUseStateBehavior inUseStateBehavior;

    @Test
    void canUpdateTest() {
        assertFalse(inUseStateBehavior.canUpdate(new DeviceEntity()));
    }

    @Test
    void canDeleteTest() {
        assertFalse(inUseStateBehavior.canDelete(new DeviceEntity()));
    }

    @Test
    void handleUpdateTest(){
        assertThrows(IllegalStateException.class, () ->
                        inUseStateBehavior.handleUpdate(new DeviceEntity(), "New Name", "New Brand",
                                StateDeviceEnum.AVAILABLE));
    }
}
