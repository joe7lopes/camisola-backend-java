package com.camisola10.camisolabackend.adapter.rest.settings;

import com.camisola10.camisolabackend.adapter.rest.ApiUrl;
import com.camisola10.camisolabackend.application.port.in.RetrieveSettingsUseCase;
import com.camisola10.camisolabackend.domain.settings.Settings;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiUrl.SETTINGS)
@AllArgsConstructor
class SettingsController {

    private final RetrieveSettingsUseCase retrieveSettingsUseCase;

    @GetMapping
    Settings getSettings() {
        return retrieveSettingsUseCase.getSettings();
    }
}
