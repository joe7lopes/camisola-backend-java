package com.camisola10.camisolabackend.application.service;

import com.camisola10.camisolabackend.application.port.in.command.order.CreateOrderCommand;
import com.camisola10.camisolabackend.application.port.in.command.order.UpdateOrderStatusCommand;
import com.camisola10.camisolabackend.application.port.out.OrderDB;
import com.camisola10.camisolabackend.domain.order.Order;
import com.camisola10.camisolabackend.domain.order.Order.OrderId;
import com.camisola10.camisolabackend.domain.order.ShippingAddress;
import com.camisola10.camisolabackend.domain.product.Product;
import com.camisola10.camisolabackend.domain.product.ProductSize;
import com.camisola10.camisolabackend.domain.product.ProductSize.ProductSizeId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.camisola10.camisolabackend.domain.order.Order.Status.PROCESSING;
import static com.camisola10.camisolabackend.domain.order.Order.Status.RECEIVED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private ProductService productService;
    @Mock
    private RandomIdGenerator randomIdGenerator;
    @Mock
    private OrderDB db;

    private OrderService service;

    @BeforeEach
    public void setUp() {
        service = new OrderService(productService, randomIdGenerator, db);
    }

    @Test
    void shouldCreateOrder() {
        var productId = Product.ProductId.from("e8d948e2-e896-4c03-9af1-23da0ff3036b");
        var productSizeId = ProductSizeId.create();
        var item1 = CreateOrderCommand.OrderItemCommand.builder()
                .productId(productId.asString())
                .sizeId(productSizeId.asString())
                .build();
        var shippingAddress = mock(ShippingAddress.class);
        var command = new CreateOrderCommand(List.of(item1), shippingAddress);
        var product = Product.builder()
                .name("p1")
                .sizes(List.of(new ProductSize(productSizeId, null, null)))
                .id(productId)
                .build();
        when(productService.findProductById(productId)).thenReturn(Optional.of(product));
        when(randomIdGenerator.base36WithDate()).thenReturn("2020-10-28-xbbn6kg");

        //when
        OrderId orderId = service.createOrder(command);

        //Then
        verify(productService).findProductById(productId);

        var orderCapture = ArgumentCaptor.forClass(Order.class);
        verify(db).save(orderCapture.capture());
        Order order = orderCapture.getValue();
        assertThat(order.getId()).isNotNull();
        assertThat(order.getItems()).hasSize(command.getItems().size());
        assertThat(order.getShippingAddress()).isEqualTo(command.getShippingAddress());
        assertThat(order.getStatus()).isEqualTo(RECEIVED);
        assertThat(order.getCreatedAt()).isInstanceOf(LocalDateTime.class);
        assertThat(orderId).isEqualTo(order.getId());
        verifyNoMoreInteractions(productService);
        verifyNoMoreInteractions(db);
    }

    @Test
    public void shouldThrowExceptionWhenProductNotFound() {
        Product.ProductId productId = Product.ProductId.create();
        var item1 = CreateOrderCommand.OrderItemCommand.builder()
                .productId(productId.asString())
                .build();
        var shippingAddress = mock(ShippingAddress.class);
        var command = new CreateOrderCommand(List.of(item1), shippingAddress);

        assertThrows(ProductNotFoundException.class, () -> service.createOrder(command));

        verify(productService).findProductById(productId);
        verifyNoMoreInteractions(productService);
    }

    @Test
    public void shouldThrowExceptionIfSizeNoFound() {
        Product.ProductId productId = Product.ProductId.create();
        ProductSizeId productSizeId = ProductSizeId.create();
        var item1 = CreateOrderCommand.OrderItemCommand.builder()
                .productId(productId.asString())
                .sizeId(productSizeId.asString())
                .build();
        var shippingAddress = mock(ShippingAddress.class);
        var command = new CreateOrderCommand(List.of(item1), shippingAddress);
        var product = Product.builder()
                .name("p1")
                .sizes(List.of(new ProductSize(ProductSizeId.create(), null, null)))
                .id(productId)
                .build();
        when(productService.findProductById(productId)).thenReturn(Optional.of(product));

        assertThrows(ProductSizeNotFoundException.class, () -> service.createOrder(command));

        verify(productService).findProductById(productId);
        verifyNoMoreInteractions(productService);
    }

    @Test
    public void shouldUpdateOrderStatus() {
        var command = UpdateOrderStatusCommand.builder()
                .orderId(OrderId.create("1234"))
                .status(PROCESSING)
                .build();

        service.updateOrderStatus(command);

        verify(db).updateOrderStatus(command.getOrderId(), command.getStatus());
        verifyNoMoreInteractions(db);
    }

}
