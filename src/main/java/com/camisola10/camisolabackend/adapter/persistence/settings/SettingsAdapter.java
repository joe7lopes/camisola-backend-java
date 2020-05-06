package com.camisola10.camisolabackend.adapter.persistence.settings;

import com.camisola10.camisolabackend.application.port.out.SettingsDB;
import com.camisola10.camisolabackend.domain.settings.Settings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class SettingsAdapter implements SettingsDB {

    private final SettingsRepository repository;

    @Override
    public Settings findAll() {
        List<Settings> settingsList = repository.findAll();
        return settingsList.size() > 0 ? settingsList.get(0) : null;
    }
}
