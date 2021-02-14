package com.camisola10.camisolabackend;

import com.camisola10.camisolabackend.aws.S3Properties;
import com.camisola10.camisolabackend.config.JwtProperties;
import com.camisola10.camisolabackend.facebook.FacebookProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;
import java.time.ZoneId;
import java.util.TimeZone;

@EnableConfigurationProperties({S3Properties.class, JwtProperties.class, FacebookProperties.class})
@EnableAsync
@SpringBootApplication
public class CamisolaBackendApplication {

    @PostConstruct
    public void init(){
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("Europe/Lisbon")));
    }

    public static void main(String[] args) {
        SpringApplication.run(CamisolaBackendApplication.class, args);
    }

}
