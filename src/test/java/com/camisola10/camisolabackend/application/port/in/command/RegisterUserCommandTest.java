package com.camisola10.camisolabackend.application.port.in.command;

import com.camisola10.camisolabackend.application.port.in.command.user.RegisterUserCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class RegisterUserCommandTest {


    @Test
    public void shouldNotAllowNull() {
        assertThrows(Exception.class, () -> new RegisterUserCommand(null, "a", "sd", "ss"));
    }

    @Test
    public void shouldNotAllowInvalidEmail() {
        assertThrows(Exception.class, () -> new RegisterUserCommand("sd", "a", "asd@sd.", "ss"));
    }

}
