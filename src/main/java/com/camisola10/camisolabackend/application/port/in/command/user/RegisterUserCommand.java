package com.camisola10.camisolabackend.application.port.in.command.user;

import com.camisola10.camisolabackend.application.port.in.command.SelfValidating;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class RegisterUserCommand extends SelfValidating<RegisterUserCommand> {

    @NotBlank
    private final String firstName;
    @NotBlank
    private final String lastName;
    @Email(message = "Email should be valid")
    @NotBlank
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
