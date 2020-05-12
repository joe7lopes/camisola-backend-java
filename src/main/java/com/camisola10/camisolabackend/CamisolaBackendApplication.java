package com.camisola10.camisolabackend;

import com.camisola10.camisolabackend.adapter.aws.S3Properties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(S3Properties.class)
public class CamisolaBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CamisolaBackendApplication.class, args);
    }

}
