package com.camisola10.camisolabackend.application.service;

import com.camisola10.camisolabackend.adapter.presistence.ProductRepository;
import com.camisola10.camisolabackend.application.port.in.CreateProductUseCase;
import com.camisola10.camisolabackend.application.port.in.command.product.CreateProductCommand;
import com.camisola10.camisolabackend.domain.Product;
import org.springframework.stereotype.Service;

@Service
public class CreateProductService implements CreateProductUseCase {
    private ProductRepository productRepository;

    public CreateProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void createProduct(CreateProductCommand command) {

        var newProduct = Product
                .create(
                        command.getName(),
                        command.getCategories(),
                        command.getSizes(),
                        command.isCustomizable(),
                        command.getImages());

        productRepository.save(newProduct);
    }
}
