package com.camisola10.camisolabackend.application.service;

import com.camisola10.camisolabackend.application.port.in.RetrieveSettingsUseCase;
import com.camisola10.camisolabackend.application.port.out.SettingsDB;
import com.camisola10.camisolabackend.domain.settings.Settings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class SettingsService implements RetrieveSettingsUseCase {

    private final SettingsDB db;

    @Override
    public Settings getSettings() {
        return db.findAll();
    }
}
