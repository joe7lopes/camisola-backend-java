package com.camisola10.camisolabackend.domain.order;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;

@Builder
@Value
public class Order {

    OrderId id;
    ShippingAddress shippingAddress;
    List<OrderItem> items;
    LocalDateTime createdAt;
    Status status;

    public Order(OrderId id, ShippingAddress shippingAddress, List<OrderItem> items, LocalDateTime createdAt, Status status) {
        this.id = id;
        this.shippingAddress = shippingAddress;
        this.items = items;
        this.createdAt = createdAt;
        this.status = status;
        validate();
    }

    private void validate() {
        if (isNull(id)) {
            throw new InvalidOrderIdException("Order Id cannot be empty");
        }

        if (isNull(items) || items.isEmpty()) {
            throw new InvalidOrderException("An order should have at least one item");
        }

        if (isNull(createdAt)) {
            throw new InvalidOrderException("An order should have a created date time");
        }

        if (isNull(status)) {
            throw new InvalidOrderException("An order should have status");
        }

        if (isNull(shippingAddress)) {
            throw new InvalidOrderException("An order should have a shipping address");
        }

    }

    enum Status {
        RECEIVED,
        PROCESSING,
        SHIPPED,
    }

    @Value
    static class OrderId {
        UUID value;

        public OrderId(UUID value) {
            this.value = value;
            validate();
        }

        private void validate() {
            if (isNull(value)) {
                throw new InvalidOrderIdException("Order Id cannot be empty");
            }
        }

        static OrderId create() {
            return new OrderId(UUID.randomUUID());
        }

        String asString() {
            return value.toString();
        }
    }
}
