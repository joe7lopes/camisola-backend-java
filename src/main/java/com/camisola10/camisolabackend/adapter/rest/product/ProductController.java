package com.camisola10.camisolabackend.adapter.rest.product;

import com.camisola10.camisolabackend.adapter.rest.ApiUrl;
import com.camisola10.camisolabackend.application.port.in.CreateProductUseCase;
import com.camisola10.camisolabackend.application.port.in.RemoveProductUseCase;
import com.camisola10.camisolabackend.application.port.in.RetrieveProductsUseCase;
import com.camisola10.camisolabackend.domain.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiUrl.PRODUCTS)
class ProductController {

    private final RetrieveProductsUseCase retrieveProductsUseCase;
    private final CreateProductUseCase createProductUseCase;
    private final ProductRequestMapper mapper;

    @GetMapping
    List<ProductResponseDto> findAll() {
        List<Product> products = retrieveProductsUseCase.getAll();
        return products.stream().map(mapper::map)
                .collect(Collectors.toList());
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    ProductResponseDto createProduct(@RequestBody CreateProductRequest dto) {
        var command = mapper.map(dto);
        Product product = createProductUseCase.createProduct(command);
        return mapper.map(product);
    }
}
