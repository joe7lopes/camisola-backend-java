package com.camisola10.camisolabackend.rest.order;

import com.camisola10.camisolabackend.application.port.in.OrderCommandService;
import com.camisola10.camisolabackend.application.port.in.OrdersQueryService;
import com.camisola10.camisolabackend.application.port.in.command.order.CreateOrderCommand;
import com.camisola10.camisolabackend.application.port.in.command.order.UpdateOrderCommand;
import com.camisola10.camisolabackend.application.service.FetchOrdersCriteria;
import com.camisola10.camisolabackend.domain.Money;
import com.camisola10.camisolabackend.domain.order.Order;
import com.camisola10.camisolabackend.domain.order.Order.OrderId;
import com.camisola10.camisolabackend.domain.order.OrderItem;
import com.camisola10.camisolabackend.domain.order.ShippingAddress;
import com.camisola10.camisolabackend.domain.product.Product;
import com.camisola10.camisolabackend.domain.product.ProductSize;
import com.camisola10.camisolabackend.domain.product.Size;
import com.camisola10.camisolabackend.rest.ApiUrl;
import com.camisola10.camisolabackend.rest.ControllerTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ControllerTest(controllers = OrderController.class)
@ActiveProfiles("local")
class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    OrderRequestMapper mapper;

    @MockBean
    OrderCommandService service;

    @MockBean
    OrdersQueryService ordersQueryService;

    @Test
    public void shouldCreateNewOrder() throws Exception {

        var shippingAddress = ShippingAddressDto.builder()
                .firstName("John")
                .lastName("Due")
                .phone("99223")
                .email("john.due@gmail.com")
                .city("Moscow")
                .postCode("443-23")
                .address("ul.. milan")
                .build();

        var request = new CreateOrderRequest(null, shippingAddress);
        String requestBody = convertToJsonString(request);

        var command = mock(CreateOrderCommand.class);
        OrderId orderId = OrderId.create("1234");
        List<OrderItem> items = List.of(OrderItem.builder()
                .product(mock(Product.class))
                .size(new ProductSize(mock(ProductSize.ProductSizeId.class), new Size("XS"), Money.from(20)))
                .build());
        var order = Order.builder()
                .id(orderId)
                .items(items)
                .shippingAddress(mock(ShippingAddress.class))
                .status(Order.Status.SHIPPED)
                .createdAt(LocalDateTime.now())
                .build();
        when(mapper.map(request)).thenReturn(command);
        when(service.createOrder(command)).thenReturn(order);

        mockMvc.perform(
                post(ApiUrl.ORDERS)
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.orderId").value(orderId.asString()))
                .andExpect(jsonPath("$.shippingCost").value("5"))
                .andExpect(jsonPath("$.total").value("25"));


        verify(mapper).map(request);
        verify(service).createOrder(any(CreateOrderCommand.class));
        verifyNoMoreInteractions(mapper);
        verifyNoMoreInteractions(service);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldUpdateOrderStatusToProcessing() throws Exception {
        var payload = "{\"status\":\"PROCESSING\", \"privateNote\": \"bla\"}";
        var command = mock(UpdateOrderCommand.class);
        when(mapper.map(eq("1"), any(UpdateOrderRequest.class))).thenReturn(command);

        mockMvc.perform(put(ApiUrl.ORDERS + "/1")
                .contentType(APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isOk());

        verify(mapper).map(eq("1"), any(UpdateOrderRequest.class));
        verify(service).updateOrder(any(UpdateOrderCommand.class));
        verifyNoMoreInteractions(mapper);
        verifyNoMoreInteractions(service);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @SuppressWarnings("unchecked")
    public void shouldFetchOrders() throws Exception {
        var orderMock = mock(Order.class);
        var orders = List.of(orderMock);
        var ordersPageable = new PageImpl<>(orders);
        Page<OrderDto> response = mock(Page.class);
        when(ordersQueryService.fetchOrders(any(FetchOrdersCriteria.class))).thenReturn(ordersPageable);
        when(mapper.map(ordersPageable)).thenReturn(response);

        //WHEN
        mockMvc.perform(get(ApiUrl.ORDERS)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
        //THEN
        verify(ordersQueryService).fetchOrders(any(FetchOrdersCriteria.class));
        verify(mapper).map(ordersPageable);
        verifyNoMoreInteractions(ordersQueryService);
        verifyNoMoreInteractions(mapper);
    }

    @Test
    public void shouldReturn403WhenFetchingOrders() throws Exception {
        mockMvc.perform(get(ApiUrl.ORDERS)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturn401WhenUpdatingOrderStatus() throws Exception {
        var payload = "{\"status\":\"PROCESSING\"}";
        mockMvc.perform(post(ApiUrl.ORDERS + "/1")
                .contentType(APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isForbidden());
    }

    private String convertToJsonString(CreateOrderRequest request) throws JsonProcessingException {
        ObjectMapper jsonMapper = new ObjectMapper();
        return jsonMapper.writeValueAsString(request);
    }


}
