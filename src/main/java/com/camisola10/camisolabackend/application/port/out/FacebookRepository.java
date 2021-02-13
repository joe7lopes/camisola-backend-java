package com.camisola10.camisolabackend.application.port.out;

import com.camisola10.camisolabackend.domain.facebook.FacebookSettings;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FacebookRepository extends MongoRepository<FacebookSettings, Long> {

    default Optional<FacebookSettings> getSettings(){
        return findAll().stream().findFirst();
    }
}
