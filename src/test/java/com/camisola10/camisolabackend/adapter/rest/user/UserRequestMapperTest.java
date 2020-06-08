package com.camisola10.camisolabackend.adapter.rest.user;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class UserRequestMapperTest {

    private final UserRequestMapper mapper = Mappers.getMapper(UserRequestMapper.class);

    @Test
    void shouldMapToRegisterUserCommand() {

        var request = new SignUpRequest(
                "jhon",
                "due",
                "jhon@gg.com",
                "xxd");

        var command = mapper.map(request);

        assertThat(command.getFirstName()).isEqualTo(request.getFirstName());
        assertThat(command.getLastName()).isEqualTo(request.getLastName());
        assertThat(command.getEmail().asString()).isEqualTo(request.getEmail());
        assertThat(command.getPassword()).isEqualTo(request.getPassword());
    }
}