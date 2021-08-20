package com.camisola10.camisolabackend.application.port.in;

import com.camisola10.camisolabackend.application.service.FetchOrdersCriteria;
import com.camisola10.camisolabackend.domain.order.Order;
import com.camisola10.camisolabackend.domain.order.PrebookingReport;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrdersQueryService {

    Page<Order> fetchOrders(FetchOrdersCriteria criteria);

    List<PrebookingReport> fetchPrebookingOrders();
}
