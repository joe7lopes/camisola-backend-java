package com.camisola10.camisolabackend.adapter.persistence.product;

import com.camisola10.camisolabackend.application.port.out.ProductDB;
import com.camisola10.camisolabackend.domain.product.Product;
import com.camisola10.camisolabackend.domain.product.Product.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
class ProductPersistenceAdapter implements ProductDB {

    private final ProductRepository repository;
    private final ProductDBMapper mapper;

    @Override
    public List<Product> findAll() {
        return repository.findAll().stream()
                .map(mapper::map)
                .collect(toList());
    }

    @Override
    public Optional<Product> findById(ProductId productId) {
        return repository.findByProductId(productId.asString())
                .map(mapper::map);
    }

    @Override
    public void save(Product product) {
        ProductDb productDb = mapper.map(product);
        repository.save(productDb);
    }

    @Override
    public void deleteById(ProductId productId) {
        repository.deleteByProductId(productId.asString());
    }

}
