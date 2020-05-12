package com.camisola10.camisolabackend.adapter.persistence.order;

import com.camisola10.camisolabackend.application.port.out.OrderDB;
import com.camisola10.camisolabackend.domain.order.Order;
import com.camisola10.camisolabackend.domain.order.Order.OrderId;
import com.camisola10.camisolabackend.domain.order.Order.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OrderPersistenceAdapter implements OrderDB {

    private final OrderRepository repository;
    private final OrderDBMapper mapper;

    @Override
    public void save(Order order) {
        OrderDb orderDb = mapper.map(order);
        repository.save(orderDb);
    }

    @Override
    public void updateOrderStatus(OrderId orderId, Status newStatus) {
        OrderDb order = repository.findByOrderId(orderId.asString())
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        order.setStatus(newStatus);
        repository.save(order);
    }
}
