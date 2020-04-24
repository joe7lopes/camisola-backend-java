package com.camisola10.camisolabackend.adapter.persistence;

import com.camisola10.camisolabackend.domain.product.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>  {

    void deleteByProductId(Product.ProductId productId);
}
