package com.camisola10.camisolabackend.adapter.rest.product;

import com.camisola10.camisolabackend.adapter.rest.ApiUrl;
import com.camisola10.camisolabackend.application.port.in.CreateProductUseCase;
import com.camisola10.camisolabackend.application.port.in.RemoveProductUseCase;
import com.camisola10.camisolabackend.application.port.in.RetrieveProductsUseCase;
import com.camisola10.camisolabackend.application.port.in.command.product.RemoveProductCommand;
import com.camisola10.camisolabackend.domain.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static io.netty.handler.codec.http.HttpHeaders.Values.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
class ProductController {

    private final RetrieveProductsUseCase retrieveProductsUseCase;
    private final CreateProductUseCase createProductUseCase;
    private final RemoveProductUseCase removeProductUseCase;
    private final ProductRequestMapper mapper;

    @GetMapping(ApiUrl.PRODUCTS)
    List<ProductResponseDto> findAll() {
        List<Product> products = retrieveProductsUseCase.getAll();
        return products.stream().map(mapper::map)
                .collect(Collectors.toList());
    }

    @PostMapping(value = ApiUrl.PRODUCTS)
    @ResponseStatus(HttpStatus.CREATED)
    ProductResponseDto createProduct(@RequestBody CreateProductRequest dto) {
        var command = mapper.map(dto);
        Product product = createProductUseCase.createProduct(command);
        return mapper.map(product);
    }

    @DeleteMapping(ApiUrl.PRODUCTS + "/{id}")
    void deleteProduct(@PathVariable String id) {
        var command = mapper.map(id);
        removeProductUseCase.removeProduct(command);
    }
}
