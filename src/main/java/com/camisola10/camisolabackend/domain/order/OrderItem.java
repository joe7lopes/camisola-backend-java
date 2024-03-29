package com.camisola10.camisolabackend.domain.order;

import com.camisola10.camisolabackend.domain.Money;
import com.camisola10.camisolabackend.domain.product.Badge;
import com.camisola10.camisolabackend.domain.product.Product;
import com.camisola10.camisolabackend.domain.product.ProductSize;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Builder
@Value
public class OrderItem {
    static final Money STAMPING_COST = Money.from(12);
    Product product;
    ProductSize size;
    String stampingName;
    String stampingNumber;
    List<Badge> badges;

    public OrderItem(Product product, ProductSize size, String stampingName, String stampingNumber, List<Badge> badges) {
        this.product = product;
        this.size = size;
        this.stampingName = stampingName;
        this.stampingNumber = stampingNumber;
        this.badges = badges;
        validate();
    }

    public Money getPrice() {
        if (isBlank(stampingName) && isBlank(stampingNumber)) {
            return size.getPrice();
        } else {
            return size.getPrice().add(STAMPING_COST);
        }
    }

    public Optional<List<Badge>> getBadges() {
        return Optional.ofNullable(badges);
    }

    private void validate() {
        if (isNull(product) || isNull(size)) {
            throw new InvalidOrderItemException("Invalid Order item: " + this);
        }
    }

    static class InvalidOrderItemException extends RuntimeException {
        public InvalidOrderItemException(String message) {
            super(message);
        }
    }
}
