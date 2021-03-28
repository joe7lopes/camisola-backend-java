package com.camisola10.camisolabackend.application.port.in.command.order;

import com.camisola10.camisolabackend.domain.order.ShippingAddress;
import com.camisola10.camisolabackend.domain.product.Badge;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.util.Objects.isNull;

@NoArgsConstructor
@Data
public class CreateOrderCommand {
    private List<OrderItemCommand> items;
    private ShippingAddress shippingAddress;

    public CreateOrderCommand(List<OrderItemCommand> items, ShippingAddress shippingAddress) {
        this.items = items;
        this.shippingAddress = shippingAddress;
        validate();
    }

    private void validate() {
        if (isNull(items) || isNull(shippingAddress)) throw new InvalidCommandException(this);
    }

    private static class InvalidCommandException extends RuntimeException {
        public InvalidCommandException(CreateOrderCommand command) {
            super("invalid command: " + command);
        }
    }

    @AllArgsConstructor
    @Builder
    @Data
    public static class OrderItemCommand {
        private String productId;
        private String sizeId;
        private String stampingName;
        private String stampingNumber;
        private List<Badge> badges;
    }
}
