package com.camisola10.camisolabackend.adapter.rest.product;

import com.camisola10.camisolabackend.adapter.rest.ApiUrl;
import com.camisola10.camisolabackend.application.port.in.CreateProductUseCase;
import com.camisola10.camisolabackend.application.port.in.RetrieveProductsUseCase;
import com.camisola10.camisolabackend.domain.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final RetrieveProductsUseCase retrieveProductsUseCase;
    private final ProductRequestMapper mapper;

    @GetMapping(ApiUrl.PRODUCTS)
    List<ProductResponseDto> findAll() {
        List<Product> products = retrieveProductsUseCase.getAll();
        return products.stream().map(mapper::map)
                .collect(Collectors.toList());
    }

    @PostMapping(ApiUrl.PRODUCTS)
    @ResponseStatus(HttpStatus.CREATED)
    void createProduct(@RequestBody CreateProductRequest dto) {
        var command = mapper.toCommand(dto);
        createProductUseCase.createProduct(command);
    }
}
