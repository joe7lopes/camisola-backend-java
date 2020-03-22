package com.camisola10.camisolabackend.adapter.presistence;

import com.camisola10.camisolabackend.domain.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository  extends MongoRepository<Product, String> {
}
