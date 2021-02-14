package com.camisola10.camisolabackend.facebook;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
class FacebookConfig {

    @Bean
    RestTemplate facebookClient() {
        return new RestTemplateBuilder()
                .rootUri("https://graph.facebook.com/v9.0")
                .build();
    }

}
