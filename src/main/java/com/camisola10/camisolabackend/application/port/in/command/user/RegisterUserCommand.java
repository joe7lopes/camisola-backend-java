package com.camisola10.camisolabackend.application.port.in.command.user;

import com.camisola10.camisolabackend.domain.EmailAddress;
import lombok.Builder;
import lombok.Getter;

import static java.util.Objects.isNull;

@Getter
@Builder
public class RegisterUserCommand {

    private final String firstName;
    private final String lastName;
    private final EmailAddress emailAddress;
    private final String password;

    private RegisterUserCommand(String firstName, String lastName, EmailAddress emailAddress, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.password = password;
        validate();
    }

    private void validate() {
        if (isNull(firstName) || isNull(lastName) || isNull(emailAddress) || isNull(password)) {
            throw new InvalidUserCommand(this);
        }
    }

    private static class InvalidUserCommand extends RuntimeException {
        public InvalidUserCommand(RegisterUserCommand command) {
            super("Invalid User Command" + command);
        }
    }
}
