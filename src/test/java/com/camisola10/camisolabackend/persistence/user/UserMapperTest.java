package com.camisola10.camisolabackend.persistence.user;

import com.camisola10.camisolabackend.domain.Email;
import com.camisola10.camisolabackend.domain.user.User;
import com.camisola10.camisolabackend.domain.user.UserId;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Set;

import static com.camisola10.camisolabackend.domain.user.Role.ADMIN;
import static com.camisola10.camisolabackend.domain.user.Role.SHOP_MANAGER;
import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    void shouldMapToUserDbFromUser() {

        var user = User.builder()
                .id(UserId.create())
                .firstName("firstName")
                .lastName("lastName")
                .email(Email.from("asd@dd.com"))
                .password("sssdsd")
                .roles(Set.of(ADMIN, SHOP_MANAGER))
                .build();

        var userDb = mapper.map(user);

        assertThat(userDb.getUserId()).isEqualTo(user.getId().asString());
        assertThat(userDb.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(userDb.getLastName()).isEqualTo(user.getLastName());
        assertThat(userDb.getEmail()).isEqualTo(user.getEmail().asString());
        assertThat(userDb.getPassword()).isEqualTo(user.getPassword());
        assertThat(userDb.getRoles()).containsAll(Set.of(ADMIN.name(), SHOP_MANAGER.name()));
    }

    @Test
    void shouldMapToUserFromUserDb(){

        var userDb = UserDb.builder()
                .userId(UserId.create().asString())
                .firstName("firstName")
                .lastName("lastName")
                .email("asd@dd.com")
                .password("sssdsd")
                .roles(Set.of("ADMIN", "SHOP_MANAGER"))
                .build();

        var user = mapper.map(userDb);

        assertThat(user.getId().asString()).isEqualTo(userDb.getUserId());
        assertThat(user.getFirstName()).isEqualTo(userDb.getFirstName());
        assertThat(user.getLastName()).isEqualTo(userDb.getLastName());
        assertThat(user.getEmail().asString()).isEqualTo(userDb.getEmail());
        assertThat(user.getPassword()).isEqualTo(userDb.getPassword());
        assertThat(user.getRoles()).containsAll(Set.of(ADMIN, SHOP_MANAGER));
    }
}