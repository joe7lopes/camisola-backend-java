package com.camisola10.camisolabackend.adapter.persistence.settings;

import com.camisola10.camisolabackend.domain.settings.Settings;
import org.springframework.data.mongodb.repository.MongoRepository;

interface SettingsRepository extends MongoRepository<Settings, String> {
}