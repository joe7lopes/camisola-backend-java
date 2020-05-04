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

    private final ProductDB db;

    @Override
    public List<Product> getAll() {
        return db.findAll();
    }


    @Override
    public void createProduct(CreateProductCommand command) {

        var newProduct = Product.builder()
                .name(command.getName())
                .categories(command.getCategories())
                .sizes(command.getSizes())
                .customizable(command.isCustomizable())
                .images(command.getImages())
                .defaultPrice(command.getDefaultPrice())
                .build()
                        ;

        db.save(newProduct);
    }

    @Override
    public void removeProduct(RemoveProductCommand command) {
        db.deleteById(command.getProductId());
    }
}
