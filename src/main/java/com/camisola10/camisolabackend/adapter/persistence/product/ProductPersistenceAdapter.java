package com.camisola10.camisolabackend.adapter.persistence.product;

import com.camisola10.camisolabackend.application.port.out.ProductDB;
import com.camisola10.camisolabackend.domain.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
class ProductPersistenceAdapter implements ProductDB {

    private final ProductRepository repository;
    private final ProductDbMapper mapper;

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
        repository.deleteById(productId.getValue());
    }
}
