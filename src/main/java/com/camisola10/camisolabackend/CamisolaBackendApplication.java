package com.camisola10.camisolabackend;

import com.camisola10.camisolabackend.adapter.persistence.ProductRepository;
import com.camisola10.camisolabackend.application.port.out.ProductDB;
import com.camisola10.camisolabackend.domain.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

@SpringBootApplication
public class CamisolaBackendApplication {

	@Autowired
	ProductRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(CamisolaBackendApplication.class, args);
	}
}
