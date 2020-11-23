package com.camisola10.camisolabackend.application.port.out;

import com.camisola10.camisolabackend.domain.EmailAddress;
import com.camisola10.camisolabackend.domain.user.User;

import java.util.Optional;

public interface UserDB {

    Optional<User> findByEmail(EmailAddress emailAddress);
}
