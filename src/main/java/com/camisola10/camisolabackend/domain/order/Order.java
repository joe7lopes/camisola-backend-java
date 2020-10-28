package com.camisola10.camisolabackend.domain.order;

import com.camisola10.camisolabackend.domain.Money;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

import static java.math.BigDecimal.ZERO;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Builder
@Value
public class Order {
    static final Money shippingCost = Money.from("5");
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

    public Money getTotal() {
        return items.stream()
                .map(OrderItem::getPrice)
                .reduce(Money.from(ZERO), Money::add)
                .add(shippingCost);
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

    public enum Status {
        RECEIVED,
        PROCESSING,
        SHIPPED,
    }

    @Value
    public static class OrderId {
        String value;

        private OrderId(String value) {
            this.value = value;
            validate();
        }

        public static OrderId create(String orderId) {
            return new OrderId(orderId);
        }

        public static OrderId from(String orderId) {
            return new OrderId(orderId);
        }

        public String asString() {
            return value;
        }

        private void validate() {
            if (isNull(value) || isBlank(value)) {
                throw new InvalidOrderIdException("Order Id cannot be empty");
            }
        }
    }
}
