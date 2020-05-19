package com.camisola10.camisolabackend.application.service;

import com.camisola10.camisolabackend.application.port.in.CreateOrderUseCase;
import com.camisola10.camisolabackend.application.port.in.FetchOrdersUseCase;
import com.camisola10.camisolabackend.application.port.in.UpdateOrderStatusUseCase;
import com.camisola10.camisolabackend.application.port.in.command.order.CreateOrderCommand;
import com.camisola10.camisolabackend.application.port.in.command.order.CreateOrderCommand.OrderItemCommand;
import com.camisola10.camisolabackend.application.port.in.command.order.FetchOrdersCommand;
import com.camisola10.camisolabackend.application.port.in.command.order.UpdateOrderStatusCommand;
import com.camisola10.camisolabackend.application.port.out.OrderDB;
import com.camisola10.camisolabackend.domain.order.Order;
import com.camisola10.camisolabackend.domain.order.Order.OrderId;
import com.camisola10.camisolabackend.domain.order.OrderItem;
import com.camisola10.camisolabackend.domain.product.Product.ProductId;
import com.camisola10.camisolabackend.domain.product.ProductSize;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.camisola10.camisolabackend.domain.order.Order.Status.RECEIVED;

@Service
@RequiredArgsConstructor
class OrderService implements CreateOrderUseCase, UpdateOrderStatusUseCase, FetchOrdersUseCase {

    private final ProductService productService;
    private final OrderDB db;

    @Override
    public OrderId createOrder(CreateOrderCommand command) {

        var items = command.getItems().stream()
                .map(this::mapItems)
                .collect(Collectors.toList());

        var order = Order.builder()
                .id(OrderId.create())
                .items(items)
                .shippingAddress(command.getShippingAddress())
                .status(RECEIVED)
                .createdAt(LocalDateTime.now())
                .build();

        db.save(order);
        return order.getId();
    }

    @Override
    public void updateOrderStatus(UpdateOrderStatusCommand command) {
        db.updateOrderStatus(command.getOrderId(), command.getStatus());
    }

    @Override
    public List<Order> fetchOrders(FetchOrdersCommand command) {
        return null;
    }

    private OrderItem mapItems(OrderItemCommand item) {
        var productId = ProductId.from(item.getProductId());
        var product = productService.findProductById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with id: " + item.getProductId() + " not found"));

        var sizeId = ProductSize.ProductSizeId.from(item.getSizeId());
        var size = product.getSizes().stream()
                .filter(s -> s.getId().equals(sizeId))
                .findAny()
                .orElseThrow(() -> new ProductSizeNotFoundException(String.format("Invalid SizeId %s for Product %s ", sizeId, product)));

        return OrderItem.builder()
                .product(product)
                .size(size)
                .stampingName(item.getStampingName())
                .stampingNumber(item.getStampingNumber())
                .build();
    }

}
