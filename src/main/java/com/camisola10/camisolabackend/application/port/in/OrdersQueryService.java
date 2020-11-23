package com.camisola10.camisolabackend.application.port.in;

import com.camisola10.camisolabackend.application.port.in.command.order.FetchOrdersByStatusCommand;
import com.camisola10.camisolabackend.domain.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrdersQueryService {

    Page<Order> fetchOrders(Pageable pageable);
}
