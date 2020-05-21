package com.camisola10.camisolabackend.adapter.persistence.order;

import com.camisola10.camisolabackend.domain.order.Order;
import com.camisola10.camisolabackend.domain.order.Order.OrderId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface OrderDBMapper {

    @Mapping(target="orderId", source="id")
    @Mapping(target="lastModified", ignore = true)
    @Mapping(target="id", ignore = true)
    OrderDb map(Order order);

    default String map(OrderId orderId){
        return orderId.asString();
    }

    @Mapping(target = "id", source = "orderId")
    Order map(OrderDb orderDb);

    default OrderId map(String orderId){
        return OrderId.from(orderId);
    }
}
