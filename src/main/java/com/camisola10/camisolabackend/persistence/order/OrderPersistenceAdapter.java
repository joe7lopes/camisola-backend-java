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
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public Page<Order> findByCriteria(FetchOrdersCriteria criteria){
        var queryCriteria = createCriteria(criteria);
        var pageable = PageRequest.of(criteria.getPage(), criteria.getPageSize(), Sort.by(criteria.getSortBy()).descending());
        var query = Query.query(queryCriteria).with(pageable);

        var orders = mongoTemplate.find(query, OrderDb.class).stream()
                .map(mapper::map)
                .collect(Collectors.toList());

        return PageableExecutionUtils.getPage(
                orders,
                pageable,
                () -> mongoTemplate.count(query, OrderDb.class));
    }

    @Override
    public void save(Order order) {
        OrderDb orderDb = mapper.map(order);
        repository.save(orderDb);

    }

    @Override
    public Order updateOrderStatus(OrderId orderId, Status newStatus) {
        OrderDb order = repository.findByOrderId(orderId.asString())
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        order.setStatus(newStatus);
        repository.save(order);
        return mapper.map(order);
    }

    private Criteria createCriteria(FetchOrdersCriteria criteria) {
        var orderId = criteria.getOrderId();
        var name = criteria.getName();
        var phone = criteria.getPhone();
        var createdAt = criteria.getCreatedAt();
        var queryCriteria = new Criteria();


        if (Objects.nonNull(orderId)) {
            queryCriteria.and("orderId").is(orderId);
        }

        if (Objects.nonNull(phone)) {
            queryCriteria.and("shippingAddress.phone").is(phone);
        }

        if (Objects.nonNull(createdAt)) {
            LocalDate date = LocalDate.parse(createdAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            queryCriteria.andOperator(Criteria.where("createdAt").gte(date).lt(date.plusDays(1)));
        }

        if (Objects.nonNull(name)) {
            queryCriteria.orOperator(Criteria.where("shippingAddress.firstName").regex(name, "i"),
                    Criteria.where("shippingAddress.lastName").regex(name, "i"));
        }

        return queryCriteria;
    }
}
