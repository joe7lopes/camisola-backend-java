package com.camisola10.camisolabackend.persistence.user;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

interface UserRepository extends MongoRepository<UserDb, String> {

    Optional<UserDb> findByEmail(String email);

}
