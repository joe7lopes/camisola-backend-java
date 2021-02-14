package com.camisola10.camisolabackend.facebook;

import com.camisola10.camisolabackend.application.port.out.FacebookMedia;
import com.camisola10.camisolabackend.domain.facebook.FacebookPageReviews;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
@Slf4j
class FacebookAdapter implements FacebookMedia {

    private final FacebookProperties facebookProperties;
    private final RestTemplate facebookClient;

    @Override
    public String exchangeUserTokenToLongLivedToken(String userToken) {
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

    @Override
    public String exchangeLongLivedTokenToLongLivedPageToken(String userLongLivedToken) {
        val pageAccessTokenUri = UriComponentsBuilder
                .fromHttpUrl(facebookProperties.getApi() + "/" + facebookProperties.getUserId() + "/accounts")
                .queryParam("access_token", userLongLivedToken);

        val pageAccessTokenResponse = facebookClient
                .getForObject(pageAccessTokenUri.toUriString(), PageAccessTokenResponse.class);

        return pageAccessTokenResponse.getData().get(0).getAccessToken();
    }

    @Override
    public FacebookPageReviews getFacebookPageReviews(String longLivedPageAccessToken) throws RestClientException {
        val fbRatingsUri = UriComponentsBuilder
                .fromHttpUrl(facebookProperties.getApi() + "/" + facebookProperties.getPageId() + "/ratings?limit=10")
                .queryParam("access_token", longLivedPageAccessToken);

        return facebookClient.getForObject(fbRatingsUri.toUriString(), FacebookPageReviews.class);
    }
}
