package com.camisola10.camisolabackend.adapter.rest.product;

import com.camisola10.camisolabackend.adapter.rest.ApiUrl;
import com.camisola10.camisolabackend.application.port.in.ProductsCommandService;
import com.camisola10.camisolabackend.application.port.in.ProductsQueryService;
import com.camisola10.camisolabackend.application.port.in.command.product.RemoveProductCommand;
import com.camisola10.camisolabackend.domain.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    private final ProductsQueryService productsQueryService;
    private final ProductsCommandService productsCommandService;
    private final ProductRequestMapper mapper;

    @GetMapping
    List<ProductResponseDto> findAll() {
        List<Product> products = productsQueryService.getAll();
        return products.stream().map(mapper::map)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ProductResponseDto createProduct(@RequestBody CreateProductRequest dto) {
        var command = mapper.map(dto);
        Product product = productsCommandService.createProduct(command);
        return mapper.map(product);
    }

    @PutMapping("/{id}")
    ProductResponseDto updateProduct(@RequestBody UpdateProductRequest dto) {
        var command = mapper.map(dto);
        var product = productsCommandService.updateProduct(command);
        return mapper.map(product);
    }

    @DeleteMapping("/{id}")
    void deleteProduct(@PathVariable String id){
        var command = new RemoveProductCommand(Product.ProductId.from(id));
        productsCommandService.removeProduct(command);
    }
}
