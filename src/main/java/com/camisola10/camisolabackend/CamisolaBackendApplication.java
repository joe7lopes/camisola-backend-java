package com.camisola10.camisolabackend;

import com.camisola10.camisolabackend.adapter.aws.S3Properties;
import com.camisola10.camisolabackend.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.annotation.PostConstruct;
import java.time.ZoneId;
import java.util.TimeZone;

@SpringBootApplication
@EnableConfigurationProperties({S3Properties.class, JwtProperties.class})
public class CamisolaBackendApplication {

    @PostConstruct
    public void init(){
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("Europe/Lisbon")));
    }

    public static void main(String[] args) {
        SpringApplication.run(CamisolaBackendApplication.class, args);
    }

}
