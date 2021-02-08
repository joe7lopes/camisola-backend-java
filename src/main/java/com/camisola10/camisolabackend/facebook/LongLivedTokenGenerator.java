package com.camisola10.camisolabackend.facebook;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
class LongLivedTokenGenerator {

    private final RestTemplate facebookClient;
    private final FacebookProperties facebookProperties;

    String generateToken(String userToken) {
        val longLivedAccessTokenUri = UriComponentsBuilder
                .fromHttpUrl(facebookProperties.getApi() + "/oauth/access_token")
                .queryParam("grant_type", "fb_exchange_token")
                .queryParam("client_id", facebookProperties.getAppId())
                .queryParam("client_secret", facebookProperties.getAppSecret())
                .queryParam("fb_exchange_token", userToken);

        val longLivedTokenResponse = facebookClient
                .getForObject(longLivedAccessTokenUri.toUriString(), TokenResponse.class);

        return longLivedTokenResponse.getAccessToken();
    }
}
