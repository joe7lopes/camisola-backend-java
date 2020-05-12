package com.camisola10.camisolabackend.application.service;

import com.camisola10.camisolabackend.application.port.in.CreateProductUseCase;
import com.camisola10.camisolabackend.application.port.in.RemoveProductUseCase;
import com.camisola10.camisolabackend.application.port.in.RetrieveProductsUseCase;
import com.camisola10.camisolabackend.application.port.in.command.product.CreateProductCommand;
import com.camisola10.camisolabackend.application.port.in.command.product.RemoveProductCommand;
import com.camisola10.camisolabackend.application.port.out.CloudStorage;
import com.camisola10.camisolabackend.application.port.out.ProductDB;
import com.camisola10.camisolabackend.domain.product.Product;
import com.camisola10.camisolabackend.domain.product.Product.ProductId;
import com.camisola10.camisolabackend.domain.product.ProductImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class ProductService implements CreateProductUseCase, RetrieveProductsUseCase, RemoveProductUseCase {

    private final CloudStorage cloudStorage;
    private final CommandMapper mapper;
    private final ProductDB db;

    @Override
    public List<Product> getAll() {
        return db.findAll();
    }

    @Override
    public Product createProduct(CreateProductCommand command) {
        var newProduct = mapper.map(command);
        command.getImages().forEach(image -> {
            var url = cloudStorage.store(image);
            var productImage = new ProductImage(image.getName(), url, image.isDefault());
            newProduct.addImage(productImage);
        });
        db.save(newProduct);
        return newProduct;
    }

    @Override
    public void removeProduct(RemoveProductCommand command) {
        db.deleteById(command.getProductId());
    }

    Optional<Product> findProductById(ProductId productId) {
        return db.findById(productId);
    }
}
