package com.camisola10.camisolabackend;

import com.camisola10.camisolabackend.adapter.aws.S3Properties;
import com.camisola10.camisolabackend.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({S3Properties.class, JwtProperties.class})
public class CamisolaBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CamisolaBackendApplication.class, args);
    }

}
