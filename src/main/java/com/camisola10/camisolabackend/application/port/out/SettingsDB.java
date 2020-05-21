package com.camisola10.camisolabackend.application.port.out;

import com.camisola10.camisolabackend.domain.settings.Settings;

public interface SettingsDB {

    Settings findAll();
}
