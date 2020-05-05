package com.camisola10.camisolabackend.application.service;

import com.camisola10.camisolabackend.application.port.in.command.product.CreateProductCommand;
import com.camisola10.camisolabackend.domain.product.Product;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
interface CommandMapper {

    Product map(CreateProductCommand command);

}
