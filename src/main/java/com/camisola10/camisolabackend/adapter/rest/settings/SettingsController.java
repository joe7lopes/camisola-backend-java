package com.camisola10.camisolabackend.adapter.rest.settings;

import com.camisola10.camisolabackend.adapter.rest.ApiUrl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiUrl.SETTINGS)
class SettingsController {

    @GetMapping
    String getSettings(){
        return null;
    }
}
