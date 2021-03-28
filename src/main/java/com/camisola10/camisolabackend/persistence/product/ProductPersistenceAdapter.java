package com.camisola10.camisolabackend.persistence.product;

import com.camisola10.camisolabackend.application.port.out.ProductDB;
import com.camisola10.camisolabackend.domain.product.Badge;
import com.camisola10.camisolabackend.domain.product.Product;
import com.camisola10.camisolabackend.domain.product.Product.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
class ProductPersistenceAdapter implements ProductDB {

    private final ProductRepository repository;
    private final ProductDBMapper mapper;
    private final MongoTemplate mongoTemplate;

    @Override
    public List<Product> findAll() {
        return repository.findAll().stream()
                .map(mapper::map)
                .collect(toList());
    }

    @Override
    public Optional<Product> findById(ProductId productId) {
        return repository.findById(productId.asString())
                .map(mapper::map);
    }

    @Override
    public void save(Product product) {
        ProductDb productDb = mapper.map(product);
        repository.save(productDb);
    }

    @Override
    public void deleteById(ProductId productId) {
        repository.deleteById(productId.asString());
    }


    @Override
    public void deleteUnmatchedBadges(List<Badge> badges) {
        Query query = new Query(
                Criteria.where("badges").exists(true));

        List<ProductDb> productDbs = mongoTemplate.find(query, ProductDb.class);
        productDbs.stream()
                .map(productDb -> {
                    var knownBadges = productDb.getBadges().stream()
                            .filter(badges::contains)
                            .collect(toList());
                    return productDb.withBadges(knownBadges);
                })
                .forEach(repository::save);

    }

}
