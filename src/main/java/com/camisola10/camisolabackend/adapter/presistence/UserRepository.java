package com.camisola10.camisolabackend.adapter.presistence;

import com.camisola10.camisolabackend.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
}
