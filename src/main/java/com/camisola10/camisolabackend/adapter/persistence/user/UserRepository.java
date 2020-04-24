package com.camisola10.camisolabackend.adapter.persistence.user;

import com.camisola10.camisolabackend.domain.User.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);
}
