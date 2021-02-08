package com.camisola10.camisolabackend.facebook;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
class PageAccessTokenGenerator {

    private final RestTemplate facebookClient;
    private final FacebookProperties facebookProperties;

    String generateToken(String longLivedToken) {
        val pageAccessTokenUri = UriComponentsBuilder
                .fromHttpUrl(facebookProperties.getApi() + "/" + facebookProperties.getUserId() + "/accounts")
                .queryParam("access_token", longLivedToken);

        val pageAccessTokenResponse = facebookClient
                .getForObject(pageAccessTokenUri.toUriString(), PageAccessTokenResponse.class);

        return pageAccessTokenResponse.getData().get(0).getAccessToken();
    }
}
