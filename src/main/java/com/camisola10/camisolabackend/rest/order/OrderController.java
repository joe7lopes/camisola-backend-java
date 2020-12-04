package com.camisola10.camisolabackend.rest.order;

import com.camisola10.camisolabackend.application.port.in.OrderCommandService;
import com.camisola10.camisolabackend.application.port.in.OrdersQueryService;
import com.camisola10.camisolabackend.application.port.in.command.order.UpdateOrderStatusCommand;
import com.camisola10.camisolabackend.domain.order.Order;
import com.camisola10.camisolabackend.rest.ApiUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiUrl.ORDERS)
@RequiredArgsConstructor
class OrderController {

    private final OrderCommandService orderCommandService;
    private final OrdersQueryService ordersQueryService;
    private final OrderRequestMapper mapper;

    @GetMapping
    Page<OrderDto> fetchOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(defaultValue = "createdAt") String sortBy) {
        var pageRequest = PageRequest.of(page, pageSize, Sort.by(sortBy).descending());
        Page<Order> orders = ordersQueryService.fetchOrders(pageRequest);
        return mapper.map(orders);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateOrderResponse createOrder(@RequestBody CreateOrderRequest dto) {
        var command = mapper.map(dto);
        var orderId = orderCommandService.createOrder(command);
        return new CreateOrderResponse(orderId.asString());
    }

    @PostMapping("/{orderId}")
    void updateOrderStatus(@PathVariable String orderId, @RequestBody UpdateOrderStatusRequest request) {
        UpdateOrderStatusCommand command = mapper.map(orderId, request);
        orderCommandService.updateOrderStatus(command);
    }

}
