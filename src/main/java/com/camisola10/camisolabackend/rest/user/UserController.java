package com.camisola10.camisolabackend.rest.user;

import com.camisola10.camisolabackend.rest.ApiUrl;
import com.camisola10.camisolabackend.application.port.in.SignInUseCase;
import com.camisola10.camisolabackend.application.port.in.SignUpUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiUrl.USERS)
@RequiredArgsConstructor
class UserController {

    private final SignUpUseCase signUpUseCase;
    private final SignInUseCase signInUseCase;
    private final UserRequestMapper mapper;

    @PostMapping(ApiUrl.SIGN_UP)
    void signUp(@RequestBody SignUpRequest request) {
        var command = mapper.map(request);
        signUpUseCase.signUp(command);
    }

    @PostMapping(ApiUrl.SIGN_IN)
    SignInResponse signIn(@RequestBody SignInRequest request) {
        String token = signInUseCase.signIn(request.getEmail(), request.getPassword());
        return new SignInResponse(token);
    }

}
