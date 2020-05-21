package com.camisola10.camisolabackend.application.port.out;

import com.camisola10.camisolabackend.domain.user.User;

import java.util.Optional;

public interface UserDB {

    Optional<User> findByEmail(String email);

    void save(User user);
}
