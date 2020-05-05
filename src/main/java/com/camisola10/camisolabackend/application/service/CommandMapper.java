package com.camisola10.camisolabackend.application.service;

import com.camisola10.camisolabackend.application.port.in.command.product.CreateProductCommand;
import com.camisola10.camisolabackend.domain.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface CommandMapper {

    @Mapping(target = "id", ignore = true)
    Product map(CreateProductCommand command);

}
