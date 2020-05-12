package com.camisola10.camisolabackend.adapter.rest.order;

import com.camisola10.camisolabackend.adapter.rest.ApiUrl;
import com.camisola10.camisolabackend.application.port.in.CreateOrderUseCase;
import com.camisola10.camisolabackend.application.port.in.UpdateOrderStatusUseCase;
import com.camisola10.camisolabackend.application.port.in.command.order.UpdateOrderStatusCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiUrl.ORDERS)
@RequiredArgsConstructor
class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;
    private final OrderRequestMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateOrderResponse createOrder(@RequestBody CreateOrderRequest dto) {
        var command = mapper.map(dto);
        var orderId = createOrderUseCase.createOrder(command);
        return new CreateOrderResponse(orderId.asString());
    }

    @PostMapping("/{orderId}")
    void updateOrderStatus(@PathVariable String orderId, @RequestBody UpdateOrderStatusRequest request) {
        UpdateOrderStatusCommand command = mapper.map(orderId, request);
        updateOrderStatusUseCase.updateOrderStatus(command);
    }

}
