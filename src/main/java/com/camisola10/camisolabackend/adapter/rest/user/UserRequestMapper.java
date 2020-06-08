package com.camisola10.camisolabackend.adapter.rest.user;

import com.camisola10.camisolabackend.application.port.in.command.user.RegisterUserCommand;
import com.camisola10.camisolabackend.domain.Email;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface UserRequestMapper {

    RegisterUserCommand map(SignUpRequest request);

    default Email mapEmail(String email){
        return Email.from(email);
    }
}
