package com.camisola10.camisolabackend.adapter.persistence.product;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<ProductDb, String> {

    Optional<ProductDb> findByProductId(String productId);
}
