package com.worlddevices.device_api.config;

import com.worlddevices.device_api.core.enums.StateDeviceEnum;
import com.worlddevices.device_api.core.strategy.state.AvailableStateBehavior;
import com.worlddevices.device_api.core.strategy.state.InUseStateBehavior;
import com.worlddevices.device_api.core.strategy.state.InactiveStateBehavior;
import com.worlddevices.device_api.core.strategy.state.StateBehaviorStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class StrategyConfig {

    @Bean
    public Map<StateDeviceEnum, StateBehaviorStrategy> strategyMap(
            AvailableStateBehavior available, InUseStateBehavior inUse, InactiveStateBehavior inactive) {
        return Map.of(StateDeviceEnum.AVAILABLE, available,
                StateDeviceEnum.IN_USE, inUse, StateDeviceEnum.INACTIVE, inactive);
    }

}
