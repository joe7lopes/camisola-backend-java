package com.camisola10.camisolabackend.application.port.out;

import com.camisola10.camisolabackend.domain.facebook.FacebookPageReviews;

public interface FacebookMedia {

    String exchangeUserTokenToLongLivedToken(String userToken);

    String exchangeLongLivedTokenToLongLivedPageToken(String userLongLivedToken);

    FacebookPageReviews getFacebookPageReviews(String longLivedPageAccessToken);
}
