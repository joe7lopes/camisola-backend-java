package com.camisola10.camisolabackend.application.port.in;

import com.camisola10.camisolabackend.application.port.in.command.RegisterUserCommand;

public interface SignUpUseCase {

    void signUp(RegisterUserCommand command);


}
