package com.camisola10.camisolabackend.adapter.rest.order;

import com.camisola10.camisolabackend.adapter.rest.ApiUrl;
import com.camisola10.camisolabackend.application.port.in.CreateOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiUrl.ORDERS)
@RequiredArgsConstructor
class OrderController {
    private final CreateOrderUseCase service;
    private final OrderRequestMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateOrderResponseDto createOrder(@RequestBody CreateOrderRequestDto dto) {
        var command = mapper.map(dto);
        var orderId = service.createOrder(command);
        return new CreateOrderResponseDto(orderId.asString());
    }

}
