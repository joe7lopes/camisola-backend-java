package com.camisola10.camisolabackend.persistence.order;

import com.camisola10.camisolabackend.application.port.out.OrderDB;
import com.camisola10.camisolabackend.application.service.FetchOrdersCriteria;
import com.camisola10.camisolabackend.domain.order.Order;
import com.camisola10.camisolabackend.domain.order.Order.OrderId;
import com.camisola10.camisolabackend.domain.order.Order.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OrderPersistenceAdapter implements OrderDB {

    private final OrderRepository repository;
    private final OrderDBMapper mapper;
    private final MongoTemplate mongoTemplate;

    @Override
    public Order findById(OrderId orderId) {
        return repository.findByOrderId(orderId.asString())
                .map(mapper::map)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    @Override
    public Page<Order> findByCriteria(FetchOrdersCriteria criteria) {

        var pageable = PageRequest.of(criteria.getPage(), criteria.getPageSize(), Sort.by("createdAt").descending());

        if (criteria.hasName()) {
            return repository.findByName(criteria.getName(), pageable)
                    .map(mapper::map);
        } else if (criteria.hasPhone()) {
            return repository.findByPhone(criteria.getPhone(), pageable)
                    .map(mapper::map);
        } else {
            return repository.findAll(pageable)
                    .map(mapper::map);
        }

    }

    @Override
    public void save(Order order) {
        OrderDb orderDb = mapper.map(order);
        repository.save(orderDb);

    }

    @Override
    public Order updateOrder(OrderId orderId, Status newStatus, String privateNote) {
        OrderDb order = repository.findByOrderId(orderId.asString())
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        order.setStatus(newStatus);
        order.setPrivateNote(privateNote);
        repository.save(order);
        return mapper.map(order);
    }

}
