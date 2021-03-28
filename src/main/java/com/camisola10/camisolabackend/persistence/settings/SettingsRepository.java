package com.camisola10.camisolabackend.persistence.settings;

import com.camisola10.camisolabackend.domain.settings.Settings;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SettingsRepository extends MongoRepository<Settings, String> {

    default Settings getSettings(){
        return findAll().get(0);
    }

    default Optional<Settings.ProductSettings> getProductSettings(){
        return Optional.ofNullable(getSettings().getProductSettings());
    }
}
