package com.camisola10.camisolabackend.persistence.settings;

import com.camisola10.camisolabackend.domain.settings.Settings;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SettingsRepository extends MongoRepository<Settings, String> {

    default Settings getSettings(){
        return findAll().get(0);
    }
}
