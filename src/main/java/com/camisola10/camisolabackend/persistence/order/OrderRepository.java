package com.camisola10.camisolabackend.persistence.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

interface OrderRepository extends MongoRepository<OrderDb, String> {
    Optional<OrderDb> findByOrderId(String orderId);

    @Query("{ $or:[ {'shippingAddress.firstName':{ $regex: ?0, $options: 'i' }} , {'shippingAddress.lastName':{ $regex: ?0, $options: 'i' }}]  }")
    Page<OrderDb> findByName(String firstName, PageRequest pageable);

    @Query("{'shippingAddress.phone':?0}")
    Page<OrderDb> findByPhone(String phone, PageRequest pageable);
}
