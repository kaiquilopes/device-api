package com.worlddevices.device_api.core.strategy.state;

import com.worlddevices.device_api.core.enums.StateDeviceEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.EnumMap;
import java.util.Map;

@SpringBootTest
class StateBehaviorContextTest {

    private Map<StateDeviceEnum, StateBehaviorStrategy> strategies;

    @InjectMocks
    private StateBehaviorContext context;

    @BeforeEach
    void setUp() {
        strategies = new EnumMap<>(StateDeviceEnum.class);
        for (StateDeviceEnum state : StateDeviceEnum.values()) {
            StateBehaviorStrategy strategy = mock(StateBehaviorStrategy.class);
            strategies.put(state, strategy);
        }
        context = new StateBehaviorContext(strategies);
    }

    @Test
    void testGetStrategyReturnsCorrectStrategy() {
        for (StateDeviceEnum state : StateDeviceEnum.values()) {
            StateBehaviorStrategy expectedStrategy = strategies.get(state);
            assertEquals(expectedStrategy, context.getStrategy(state), "Should return correct strategy for state: " + state);
        }
    }

    @Test
    void testGetStrategyWithNullState() {
        assertNull(context.getStrategy(null), "Should return null for null state");
    }

}