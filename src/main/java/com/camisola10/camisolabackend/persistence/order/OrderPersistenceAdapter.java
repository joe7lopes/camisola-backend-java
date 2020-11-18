package com.camisola10.camisolabackend.persistence.order;

import com.camisola10.camisolabackend.application.port.out.OrderDB;
import com.camisola10.camisolabackend.domain.order.Order;
import com.camisola10.camisolabackend.domain.order.Order.OrderId;
import com.camisola10.camisolabackend.domain.order.Order.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class OrderPersistenceAdapter implements OrderDB {

    private final OrderRepository repository;
    private final OrderDBMapper mapper;

    @Override
    public Page<Order> findAll(Pageable pageable) {
        Page<OrderDb> orders = repository.findAllByOrderByCreatedAtDesc(pageable);
        return mapper.map(orders);
    }

    @Override
    public List<Order> findOrdersByStatus(Status status) {
        //TODO: needs implementation
        return null;
    }

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
