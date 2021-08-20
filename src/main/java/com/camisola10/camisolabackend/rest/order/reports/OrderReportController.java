package com.camisola10.camisolabackend.rest.order.reports;

import com.camisola10.camisolabackend.application.port.in.OrdersQueryService;
import com.camisola10.camisolabackend.domain.order.PrebookingReport;
import com.camisola10.camisolabackend.rest.ApiUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiUrl.ORDERS_REPORTS)
@RequiredArgsConstructor
class OrderReportController {

    private final OrdersQueryService ordersQueryService;

    @GetMapping("/prebooking")
    List<PrebookingReport> fetchPrebookingOrders() {
        return ordersQueryService.fetchPrebookingOrders();
    }
}
