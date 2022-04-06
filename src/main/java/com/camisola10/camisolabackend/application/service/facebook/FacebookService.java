package com.camisola10.camisolabackend.application.service.facebook;

import com.camisola10.camisolabackend.application.port.out.FacebookMedia;
import com.camisola10.camisolabackend.application.port.out.FacebookRepository;
import com.camisola10.camisolabackend.domain.facebook.FacebookPageReviews;
import com.camisola10.camisolabackend.domain.facebook.FacebookSettings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.Period;

import static java.time.LocalDateTime.now;
import static java.util.stream.Collectors.toUnmodifiableList;

@Service
@RequiredArgsConstructor
@Slf4j
public class FacebookService {

    private final FacebookRepository facebookRepository;
    private final FacebookMedia facebookMedia;
    private static final Period LONG_LIVE_TOKEN_EXPIRATION = Period.ofDays(60);

    public void storeToken(String userToken) {

        val longLivedToken = facebookMedia.exchangeUserTokenToLongLivedToken(userToken);
        val longLivedPageToken = facebookMedia.exchangeLongLivedTokenToLongLivedPageToken(longLivedToken);

        facebookRepository.save(FacebookSettings.of(longLivedPageToken, now()));
    }

    public FacebookPageReviews getPageReviews() {
        val settings = facebookRepository.getSettings();
        val pageAccessTokenIssueDate = settings
                .flatMap(FacebookSettings::getLongLivedPageAccessTokenIssuedAt)
                .orElse(now().plus(LONG_LIVE_TOKEN_EXPIRATION));

        if (pageAccessTokenIssueDate.plus(LONG_LIVE_TOKEN_EXPIRATION).isBefore(now())) {
            return FacebookPageReviews.EMPTY;
        }

        val reviews = settings.flatMap(FacebookSettings::getLongLivedPageAccessToken)
                .map(facebookMedia::getFacebookPageReviews)
                .orElse(FacebookPageReviews.EMPTY);

        val positiveReviews = reviews.getData().stream()
                .filter(FacebookPageReviews.Review::isPositiveReview)
                .collect(toUnmodifiableList());

        return new FacebookPageReviews(positiveReviews);
    }

}
