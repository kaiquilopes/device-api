package com.worlddevices.device_api.core.strategy.state;

import com.worlddevices.device_api.core.enums.StateDeviceEnum;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class StateBehaviorContext {

    private final Map<StateDeviceEnum, StateBehaviorStrategy> strategies;

    public StateBehaviorContext(Map<StateDeviceEnum, StateBehaviorStrategy> strategies) {
        this.strategies = strategies;
    }

    public StateBehaviorStrategy getStrategy(StateDeviceEnum state) {
        return strategies.get(state);
    }

}
