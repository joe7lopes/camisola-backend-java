package com.camisola10.camisolabackend.rest.order;

import com.camisola10.camisolabackend.application.port.in.OrderCommandService;
import com.camisola10.camisolabackend.application.port.in.OrdersQueryService;
import com.camisola10.camisolabackend.application.port.in.command.order.UpdateOrderCommand;
import com.camisola10.camisolabackend.application.service.FetchOrdersCriteria;
import com.camisola10.camisolabackend.domain.order.Order;
import com.camisola10.camisolabackend.rest.ApiUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiUrl.ORDERS)
@RequiredArgsConstructor
class OrderController {

    private final OrderCommandService orderCommandService;
    private final OrdersQueryService ordersQueryService;
    private final OrderRequestMapper mapper;

    @GetMapping
    Page<OrderDto> fetchOrders(FetchOrdersCriteria criteria) {
        Page<Order> orders = ordersQueryService.fetchOrders(criteria);
        return mapper.map(orders);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateOrderResponse createOrder(@RequestBody CreateOrderRequest dto) {
        var command = mapper.map(dto);
        var order = orderCommandService.createOrder(command);
        return new CreateOrderResponse(order.getId().asString(), order.getTotal().asString(), Order.shippingCost.asString());
    }

    @PutMapping("/{orderId}")
    void updateOrder(@PathVariable String orderId, @RequestBody UpdateOrderRequest request) {
        UpdateOrderCommand command = mapper.map(orderId, request);
        orderCommandService.updateOrder(command);
    }

}
