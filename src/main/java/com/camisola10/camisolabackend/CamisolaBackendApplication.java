package com.camisola10.camisolabackend;

import com.camisola10.camisolabackend.adapter.persistence.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CamisolaBackendApplication {

	@Autowired
	ProductRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(CamisolaBackendApplication.class, args);
	}
}
