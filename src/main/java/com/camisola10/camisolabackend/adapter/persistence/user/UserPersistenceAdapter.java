package com.camisola10.camisolabackend.adapter.persistence.user;

import com.camisola10.camisolabackend.application.port.out.UserDB;
import com.camisola10.camisolabackend.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserDB {

    //private final UserRepository repository;

    @Override
    public Optional<User> findByUserName() {
        return Optional.empty();
    }
}
