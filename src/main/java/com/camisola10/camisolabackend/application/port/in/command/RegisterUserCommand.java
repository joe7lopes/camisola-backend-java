package com.camisola10.camisolabackend.application.port.in.command;

import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Value
public class RegisterUserCommand extends SelfValidating<RegisterUserCommand> {

    @NotBlank
    private final String firstName;
    @NotBlank
    private final String lastName;
    @Email(message = "Email should be valid")
    private final String email;
    @NotBlank
    private final String password;

    public RegisterUserCommand(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        validateSelf();
    }
}
