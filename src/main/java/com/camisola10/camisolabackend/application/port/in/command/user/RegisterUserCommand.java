package com.camisola10.camisolabackend.application.port.in.command.user;

import lombok.Getter;

@Getter
public class RegisterUserCommand {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;

    public RegisterUserCommand(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
