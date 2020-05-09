package com.camisola10.camisolabackend.adapter.rest.order;

import com.camisola10.camisolabackend.application.port.in.command.order.CreateOrderCommand;
import com.camisola10.camisolabackend.domain.order.Email;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class OrderRequestMapperTest {

    private OrderRequestMapper mapper = Mappers.getMapper(OrderRequestMapper.class);


    @Test
    void shouldMapFromRequestToCommand() {
        var shippingAddress = ShippingAddressDto.builder()
                .firstName("John")
                .lastName("Due")
                .phone("99223")
                .email("john.due@gmail.com")
                .city("Moscow")
                .postCode("443-23")
                .address("ul.. milan")
                .build();

        CreateOrderRequestDto dto = new CreateOrderRequestDto(null, shippingAddress);
        CreateOrderCommand command = mapper.map(dto);

        assertThat(command.getItems()).isEqualTo(dto.getItems());
        assertThat(command.getShippingAddress().getFirstName()).isEqualTo(dto.getShippingAddress().getFirstName());
        assertThat(command.getShippingAddress().getLastName()).isEqualTo(dto.getShippingAddress().getLastName());
        assertThat(command.getShippingAddress().getAddress()).isEqualTo(dto.getShippingAddress().getAddress());
        assertThat(command.getShippingAddress().getCity()).isEqualTo(dto.getShippingAddress().getCity());
        assertThat(command.getShippingAddress().getPhone()).isEqualTo(dto.getShippingAddress().getPhone());
        assertThat(command.getShippingAddress().getPostCode()).isEqualTo(dto.getShippingAddress().getPostCode());
        assertThat(command.getShippingAddress().getEmail().asString()).isEqualTo(dto.getShippingAddress().getEmail());
    }

    @Test
    void testMapStringToEmail() {
        var email = "jj@ll.com";

        Email result = mapper.map(email);

        assertThat(result.asString()).isEqualTo(email);
    }
}
