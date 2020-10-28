package com.camisola10.camisolabackend.adapter.rest.order;

import com.camisola10.camisolabackend.adapter.rest.ApiUrl;
import com.camisola10.camisolabackend.adapter.rest.ControllerTest;
import com.camisola10.camisolabackend.application.port.in.OrderCommandService;
import com.camisola10.camisolabackend.application.port.in.OrdersQueryService;
import com.camisola10.camisolabackend.application.port.in.command.order.CreateOrderCommand;
import com.camisola10.camisolabackend.application.port.in.command.order.UpdateOrderStatusCommand;
import com.camisola10.camisolabackend.domain.order.Order;
import com.camisola10.camisolabackend.domain.order.Order.OrderId;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        when(mapper.map(request)).thenReturn(command);
        when(service.createOrder(command)).thenReturn(orderId);

        mockMvc.perform(
                post(ApiUrl.ORDERS)
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.orderId").value(orderId.asString()));


        verify(mapper).map(request);
        verify(service).createOrder(any(CreateOrderCommand.class));
        verifyNoMoreInteractions(mapper);
        verifyNoMoreInteractions(service);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldUpdateOrderStatusToProcessing() throws Exception {
        var payload = "{\"status\":\"PROCESSING\"}";
        var command = mock(UpdateOrderStatusCommand.class);
        when(mapper.map(eq("1"), any(UpdateOrderStatusRequest.class))).thenReturn(command);

        mockMvc.perform(post(ApiUrl.ORDERS + "/1")
                .contentType(APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isOk());

        verify(mapper).map(eq("1"), any(UpdateOrderStatusRequest.class));
        verify(service).updateOrderStatus(any(UpdateOrderStatusCommand.class));
        verifyNoMoreInteractions(mapper);
        verifyNoMoreInteractions(service);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldFetchOrders() throws Exception {
        var orderMock = mock(Order.class);
        var orders = List.of(orderMock);
        FetchOrdersResponse fetchOrdersResponse = mock(FetchOrdersResponse.class);
        when(ordersQueryService.fetchOrders()).thenReturn(orders);
        when(mapper.map(orders)).thenReturn(fetchOrdersResponse);

        mockMvc.perform(get(ApiUrl.ORDERS)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(ordersQueryService).fetchOrders();
        verify(mapper).map(orders);
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
    public void shouldReturn403WhenUpdatingOrderStatus() throws Exception {
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
