package com.camisola10.camisolabackend.application.service.facebook;

import com.camisola10.camisolabackend.application.port.out.FacebookMedia;
import com.camisola10.camisolabackend.domain.facebook.FacebookPageReviews;
import com.camisola10.camisolabackend.domain.settings.Settings;
import com.camisola10.camisolabackend.persistence.settings.SettingsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FacebookService {

    private final SettingsRepository settingsRepository;
    private final FacebookMedia facebookMedia;

    public void storeToken(String userToken) {
        Settings settings = settingsRepository.getSettings();
        settings.setFbToken(userToken);
        settingsRepository.save(settings);
    }

    public FacebookPageReviews getPageReviews() {
        Settings settings = settingsRepository.getSettings();
        return facebookMedia.getFacebookPageReviews(settings.getFbToken());
    }

}
