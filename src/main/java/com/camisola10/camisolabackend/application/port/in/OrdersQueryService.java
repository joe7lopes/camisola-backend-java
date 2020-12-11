package com.camisola10.camisolabackend.application.port.in;

import com.camisola10.camisolabackend.application.service.FetchOrdersCriteria;
import com.camisola10.camisolabackend.domain.order.Order;
import org.springframework.data.domain.Page;

public interface OrdersQueryService {

    Page<Order> fetchOrders(FetchOrdersCriteria criteria);
}
