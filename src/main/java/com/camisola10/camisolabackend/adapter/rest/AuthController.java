package com.camisola10.camisolabackend.adapter.rest;

import com.camisola10.camisolabackend.adapter.rest.dto.SignUpDto;
import com.camisola10.camisolabackend.application.port.in.SignUpUseCase;
import com.camisola10.camisolabackend.application.port.in.command.RegisterUserCommand;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

    private SignUpUseCase signUpUseCase;

    public AuthController(SignUpUseCase signUpUseCase) {
        this.signUpUseCase = signUpUseCase;
    }

    @PostMapping(ApiUrl.SIGN_UP_URL)
    public void signUp(@Valid @RequestBody SignUpDto signUpDTO) {
        RegisterUserCommand command = new RegisterUserCommand(signUpDTO.firstName, signUpDTO.lastName, signUpDTO.email, signUpDTO.password);
        System.out.println("runned");
        signUpUseCase.signUp(command);
    }

}
