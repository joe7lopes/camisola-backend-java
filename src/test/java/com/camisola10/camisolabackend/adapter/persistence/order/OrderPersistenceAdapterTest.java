package com.camisola10.camisolabackend.adapter.persistence.order;

import com.camisola10.camisolabackend.application.port.out.OrderDB;
import com.camisola10.camisolabackend.domain.order.Order;
import com.camisola10.camisolabackend.domain.order.OrderItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.camisola10.camisolabackend.domain.order.Order.OrderId.create;
import static com.camisola10.camisolabackend.domain.order.Order.Status.PROCESSING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderPersistenceAdapterTest {

    @Mock
    private OrderRepository repository;
    @Mock
    private OrderDBMapper mapper;

    private OrderDB adapter;

    @BeforeEach
    public void setUp() {
        adapter = new OrderPersistenceAdapter(repository, mapper);
    }

    @Test
    public void shouldSaveOrder() {
        var orderMock = mock(Order.class);
        var orderDbMock = mock(OrderDb.class);
        when(mapper.map(orderMock)).thenReturn(orderDbMock);

        adapter.save(orderMock);

        verify(repository).save(orderDbMock);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldThrowExceptionForNonExistingOrder() {
        var orderId = create("1234");
        assertThrows(OrderNotFoundException.class,
                () -> adapter.updateOrderStatus(orderId, PROCESSING));
    }

    @Test
    public void shouldUpdateOrderToNewStatusAndSave() {
        var orderId = create("1234");
        var item1 = mock(OrderItem.class);
        var order = OrderDb.builder()
                .orderId(orderId.asString())
                .items(List.of(item1))
                .build();
        when(repository.findByOrderId(orderId.asString())).thenReturn(Optional.of(order));

        adapter.updateOrderStatus(orderId, PROCESSING);

        assertThat(order.getOrderId()).isEqualTo(orderId.asString());
        assertThat(order.getStatus()).isEqualTo(PROCESSING);
        assertThat(order.getItems()).hasSize(1);
        verify(repository).save(order);
        verifyNoMoreInteractions(repository);
    }
}
