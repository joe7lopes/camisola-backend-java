package com.camisola10.camisolabackend.application.port.out;

import com.camisola10.camisolabackend.domain.facebook.FacebookPageReviews;

public interface FacebookMedia {

    FacebookPageReviews getFacebookPageReviews(String userToken);

}
