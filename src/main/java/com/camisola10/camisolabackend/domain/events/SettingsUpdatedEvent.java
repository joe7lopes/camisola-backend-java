package com.camisola10.camisolabackend.domain.events;

import com.camisola10.camisolabackend.domain.settings.Settings;
import lombok.Value;

import java.util.UUID;

@Value
public class SettingsUpdatedEvent {
    UUID id = UUID.randomUUID();
    Settings settings;

    public static SettingsUpdatedEvent of(Settings settings){
        return new SettingsUpdatedEvent(settings);
    }
}
