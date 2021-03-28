package com.camisola10.camisolabackend.domain.events;

public interface SettingsUpdatedListener {

    void handleEvent(SettingsUpdatedEvent event);

}
