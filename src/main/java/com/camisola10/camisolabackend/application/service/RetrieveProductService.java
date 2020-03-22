package com.camisola10.camisolabackend.application.service;

import com.camisola10.camisolabackend.adapter.presistence.ProductRepository;
import com.camisola10.camisolabackend.application.port.in.RetrieveProductsUseCase;
import com.camisola10.camisolabackend.domain.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RetrieveProductService implements RetrieveProductsUseCase {

    ProductRepository repository;

    public RetrieveProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> getAll() {
        return repository.findAll();
    }
}
