package com.camisola10.camisolabackend.adapter.rest.order;

import com.camisola10.camisolabackend.adapter.rest.ApiUrl;
import com.camisola10.camisolabackend.application.port.in.CreateOrderUseCase;
import com.camisola10.camisolabackend.application.port.in.command.order.CreateOrderCommand;
import com.camisola10.camisolabackend.domain.order.Order.OrderId;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    OrderRequestMapper mapper;

    @MockBean
    CreateOrderUseCase service;

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

        var request = new CreateOrderRequestDto(null, shippingAddress);
        String requestBody = convertToJsonString(request);

        var command = mock(CreateOrderCommand.class);
        OrderId orderId = OrderId.create();
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

    private String convertToJsonString(CreateOrderRequestDto request) throws JsonProcessingException {
        ObjectMapper jsonMapper = new ObjectMapper();
        return jsonMapper.writeValueAsString(request);
    }
}
