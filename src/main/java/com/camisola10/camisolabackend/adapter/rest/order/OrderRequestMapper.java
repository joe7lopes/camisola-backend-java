package com.camisola10.camisolabackend.adapter.rest.order;

import com.camisola10.camisolabackend.application.port.in.command.order.CreateOrderCommand;
import com.camisola10.camisolabackend.domain.order.Email;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface OrderRequestMapper {

    CreateOrderCommand map(CreateOrderRequestDto dto);

    default Email map(String email) {
        return new Email(email);
    }
}
