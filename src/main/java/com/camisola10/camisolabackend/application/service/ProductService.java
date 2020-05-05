package com.camisola10.camisolabackend.application.service;

import com.camisola10.camisolabackend.application.port.in.CreateProductUseCase;
import com.camisola10.camisolabackend.application.port.in.RemoveProductUseCase;
import com.camisola10.camisolabackend.application.port.in.RetrieveProductsUseCase;
import com.camisola10.camisolabackend.application.port.in.command.product.CreateProductCommand;
import com.camisola10.camisolabackend.application.port.in.command.product.RemoveProductCommand;
import com.camisola10.camisolabackend.application.port.out.ProductDB;
import com.camisola10.camisolabackend.domain.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class ProductService implements CreateProductUseCase, RetrieveProductsUseCase, RemoveProductUseCase {

    private final CommandMapper mapper;
    private final ProductDB db;

    @Override
    public List<Product> getAll() {
        return db.findAll();
    }


    @Override
    public Product createProduct(CreateProductCommand command) {
        var newProduct = mapper.map(command);
        db.save(newProduct);
        return newProduct;
    }

    @Override
    public void removeProduct(RemoveProductCommand command) {
        db.deleteById(command.getProductId());
    }
}
