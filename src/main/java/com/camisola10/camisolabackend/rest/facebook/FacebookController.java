package com.camisola10.camisolabackend.rest.facebook;

import com.camisola10.camisolabackend.application.service.facebook.FacebookService;
import com.camisola10.camisolabackend.domain.facebook.FacebookPageReviews;
import com.camisola10.camisolabackend.rest.ApiUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
class FacebookController {

    private final FacebookService facebookService;

    @PostMapping(ApiUrl.FB)
    void updateToken(@RequestBody SaveTokenRequest request){
        facebookService.storeToken(request.getUserToken());
    }

    @GetMapping(ApiUrl.FB_REVIEWS)
    FacebookPageReviews getReviews(){
        return facebookService.getPageReviews();
    }
}
