package com.camisola10.camisolabackend.rest.user;

import com.camisola10.camisolabackend.rest.ApiUrl;
import com.camisola10.camisolabackend.application.port.in.SignInUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiUrl.USERS)
@RequiredArgsConstructor
class UserController {

    private final SignInUseCase signInUseCase;

    @PostMapping(ApiUrl.SIGN_IN)
    SignInResponse signIn(@RequestBody SignInRequest request) {
        String token = signInUseCase.signIn(request.getEmail(), request.getPassword());
        return new SignInResponse(token);
    }

}
