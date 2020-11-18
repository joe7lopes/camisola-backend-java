package com.camisola10.camisolabackend.persistence.user;

import com.camisola10.camisolabackend.application.port.out.UserDB;
import com.camisola10.camisolabackend.domain.Email;
import com.camisola10.camisolabackend.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserDB {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public Optional<User> findByEmail(Email email) {
        return repository.findByEmail(email.asString())
                .map(mapper::map);
    }

    @Override
    public void save(User user) {
        var userDb = mapper.map(user);
        repository.save(userDb);
    }

}
