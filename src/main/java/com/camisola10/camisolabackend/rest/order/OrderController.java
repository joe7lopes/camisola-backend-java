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
    Page<OrderDto> fetchOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false) String orderId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String createdAt
    ) {

        var criteria = FetchOrdersCriteria.builder()
                .page(page)
                .pageSize(pageSize)
                .sortBy(sortBy)
                .orderId(orderId)
                .name(name)
                .phone(phone)
                .createdAt(createdAt)
                .build();

        Page<Order> orders = ordersQueryService.fetchOrders(criteria);
        return mapper.map(orders);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateOrderResponse createOrder(@RequestBody CreateOrderRequest dto) {
        var command = mapper.map(dto);
        var orderId = orderCommandService.createOrder(command);
        return new CreateOrderResponse(orderId.asString());
    }

    @PutMapping("/{orderId}")
    void updateOrder(@PathVariable String orderId, @RequestBody UpdateOrderRequest request) {
        UpdateOrderCommand command = mapper.map(orderId, request);
        orderCommandService.updateOrder(command);
    }

}
