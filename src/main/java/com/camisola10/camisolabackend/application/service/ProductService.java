package com.camisola10.camisolabackend.application.service;

import com.camisola10.camisolabackend.adapter.presistence.ProductRepository;
import com.camisola10.camisolabackend.application.port.in.CreateProductUseCase;
import com.camisola10.camisolabackend.application.port.in.RemoveProductUseCase;
import com.camisola10.camisolabackend.application.port.in.command.product.CreateProductCommand;
import com.camisola10.camisolabackend.application.port.in.command.product.RemoveProductCommand;
import com.camisola10.camisolabackend.domain.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements RemoveProductUseCase {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public void removeProduct(RemoveProductCommand command) {
        productRepository.deleteById(command.getProductId());
    }


}
