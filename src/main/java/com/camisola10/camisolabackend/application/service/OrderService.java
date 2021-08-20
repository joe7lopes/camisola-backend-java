package com.camisola10.camisolabackend.application.service;

import com.camisola10.camisolabackend.application.port.in.OrderCommandService;
import com.camisola10.camisolabackend.application.port.in.OrdersQueryService;
import com.camisola10.camisolabackend.application.port.in.ProductsQueryService;
import com.camisola10.camisolabackend.application.port.in.command.order.CreateOrderCommand;
import com.camisola10.camisolabackend.application.port.in.command.order.CreateOrderCommand.OrderItemCommand;
import com.camisola10.camisolabackend.application.port.in.command.order.UpdateOrderCommand;
import com.camisola10.camisolabackend.application.port.out.OrderDB;
import com.camisola10.camisolabackend.domain.events.OrderCreatedEvent;
import com.camisola10.camisolabackend.domain.events.OrderStatusUpdatedEvent;
import com.camisola10.camisolabackend.domain.order.Order;
import com.camisola10.camisolabackend.domain.order.Order.OrderId;
import com.camisola10.camisolabackend.domain.order.OrderItem;
import com.camisola10.camisolabackend.domain.order.PrebookingReport;
import com.camisola10.camisolabackend.domain.product.Product;
import com.camisola10.camisolabackend.domain.product.Product.ProductId;
import com.camisola10.camisolabackend.domain.product.ProductSize;
import com.camisola10.camisolabackend.domain.settings.Settings;
import com.camisola10.camisolabackend.persistence.settings.SettingsRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.camisola10.camisolabackend.domain.order.Order.Status.RECEIVED;
import static java.time.LocalDateTime.now;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
class OrderService implements OrderCommandService, OrdersQueryService {

    private final ProductsQueryService productService;
    private final SettingsRepository settingsRepository;
    private final RandomIdGenerator randomIdGenerator;
    private final OrderDB db;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public Order createOrder(CreateOrderCommand command) {

        var items = command.getItems().stream()
                .map(this::mapItems)
                .collect(toList());

        var orderId = randomIdGenerator.base36WithDate();
        var order = Order.builder()
                .id(OrderId.create(orderId))
                .items(items)
                .shippingAddress(command.getShippingAddress())
                .status(RECEIVED)
                .createdAt(now())
                .build();

        db.save(order);
        eventPublisher.publishEvent(new OrderCreatedEvent(order));
        return order;
    }

    @Override
    public void updateOrder(UpdateOrderCommand command) {
        Order order = db.updateOrder(
                command.getOrderId(),
                command.getStatus(),
                command.getPrivateNote());

        eventPublisher.publishEvent(new OrderStatusUpdatedEvent(order));
    }

    @Override
    public Page<Order> fetchOrders(FetchOrdersCriteria criteria) {
        return db.findByCriteria(criteria);
    }

    @Override
    public List<PrebookingReport> fetchPrebookingOrders() {
        var orders = db.findPrebookingOrders();

        var items = orders.stream()
                .flatMap(order -> order.getItems().stream())
                .filter(item -> item.getProduct().isPrebooking())
                .collect(Collectors.toMap(ItemKey::new, (item) -> 1, Integer::sum));

        return items.entrySet().stream()
                .map(entry -> new PrebookingReport(entry.getKey().product.getName(), entry.getKey().size.getSize().asString(), entry.getValue()))
                .collect(Collectors.toUnmodifiableList());
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

        var badges = settingsRepository.getProductSettings()
                .map(Settings.ProductSettings::getBadges)
                .orElse(emptyList())
                .stream()
                .filter(badge -> item.getBadges().contains(badge))
                .collect(toList());

        return OrderItem.builder()
                .product(product)
                .size(size)
                .stampingName(item.getStampingName())
                .stampingNumber(item.getStampingNumber())
                .badges(badges)
                .build();
    }

    @Data
    private static class ItemKey {
        private final Product product;
        private final ProductSize size;

        public ItemKey(Product product, ProductSize size) {
            this.product = product;
            this.size = size;
        }

        ItemKey(OrderItem orderItem) {
            this(orderItem.getProduct(), orderItem.getSize());
        }

    }

}
