package com.camisola10.camisolabackend.adapter.persistence.order;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

interface OrderRepository extends MongoRepository<OrderDb, String> {
    Optional<OrderDb> findByOrderId(String orderId);

    List<OrderDb> findAllByOrderByCreatedAtDesc();
}
