package com.camisola10.camisolabackend.adapter.persistence.product;

import com.camisola10.camisolabackend.application.port.out.ProductDB;
import com.camisola10.camisolabackend.domain.product.Product;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
class ProductPersistenceAdapter implements ProductDB {

    private ProductRepository repository;
    private ProductDBMapper mapper;

    public ProductPersistenceAdapter(ProductRepository repository, ProductDBMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll().stream()
                .map(mapper::map)
                .collect(toList());
    }

    @Override
    public void save(Product product) {
        ProductDb productDb = mapper.map(product);
        repository.save(productDb);
    }

    @Override
    public void deleteById(Product.ProductId productId) {
        repository.deleteById(productId.getValue().toString());
    }
}
