package com.camisola10.camisolabackend.persistence.order;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

interface OrderRepository extends MongoRepository<OrderDb, String> {
    Optional<OrderDb> findByOrderId(String orderId);
}
