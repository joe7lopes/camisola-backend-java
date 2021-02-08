package com.camisola10.camisolabackend.facebook;

import com.camisola10.camisolabackend.application.port.out.FacebookMedia;
import com.camisola10.camisolabackend.application.service.facebook.FacebookService;
import com.camisola10.camisolabackend.domain.facebook.FacebookPageReviews;
import com.camisola10.camisolabackend.persistence.settings.SettingsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
@Slf4j
class FacebookAdapter implements FacebookMedia {

    private final FacebookProperties facebookProperties;
    private final LongLivedTokenGenerator longLivedTokenGenerator;
    private final PageAccessTokenGenerator pageAccessTokenGenerator;
    private final RestTemplate facebookClient;

    @Override
    public FacebookPageReviews getFacebookPageReviews(String userToken) {
        try {
            val longLivedToken = longLivedTokenGenerator.generateToken(userToken);
            val pageAccessToken = pageAccessTokenGenerator.generateToken(longLivedToken);
            val fbRatingsUri = UriComponentsBuilder
                    .fromHttpUrl(facebookProperties.getApi() + "/" + facebookProperties.getPageId() + "/ratings")
                    .queryParam("access_token", pageAccessToken);

            return facebookClient.getForObject(fbRatingsUri.toUriString(), FacebookPageReviews.class);
        } catch (Exception e) {
            //TODO: handle token expire.
            log.error("Error getting facebook reviews", e);
            return FacebookPageReviews.EMPTY;
        }
    }
}
