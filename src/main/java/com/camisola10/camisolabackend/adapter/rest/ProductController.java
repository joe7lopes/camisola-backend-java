package com.camisola10.camisolabackend.adapter.rest;

import com.camisola10.camisolabackend.adapter.rest.dto.CreateProductDto;
import com.camisola10.camisolabackend.application.port.in.CreateProductUseCase;
import com.camisola10.camisolabackend.application.port.in.command.product.CreateProductCommand;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private CreateProductUseCase createProductUseCase;

    public ProductController(CreateProductUseCase createProductUseCase) {
        this.createProductUseCase = createProductUseCase;
    }

    @PostMapping(ApiUrl.CREATE_PRODUCT)
    void createProduct(@RequestBody CreateProductDto dto) {

        var command = new CreateProductCommand(
                dto.getName(),
                dto.getImages(),
                dto.getSizes(),
                dto.getCategories(),
                dto.isCustomizable()
        );
        createProductUseCase.createProduct(command);
    }
}
