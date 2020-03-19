package com.camisola10.camisolabackend.application.port.in;


public interface SignInUseCase {

    void signIn(String email, String password);

    class SignInCommand {
    }
}
