package com.camisola10.camisolabackend.rest.settings;

import com.camisola10.camisolabackend.domain.settings.Settings;
import com.camisola10.camisolabackend.persistence.settings.SettingsRepository;
import com.camisola10.camisolabackend.rest.ApiUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiUrl.SETTINGS)
@RequiredArgsConstructor
class SettingsController {

    private final SettingsRepository repository;

    @GetMapping
    public Settings getAll() {
        return repository.findAll().get(0);
    }

    @PostMapping
    public Settings create(@RequestBody Settings settings) {
        return repository.save(settings);
    }

}