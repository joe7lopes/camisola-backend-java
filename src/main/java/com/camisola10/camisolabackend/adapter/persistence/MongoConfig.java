package com.camisola10.camisolabackend.adapter.persistence;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing
class MongoConfig {

//    @Bean
//    MongoTransactionManager transactionManager(MongoDbFactory mongoDbFactory) {
//        return new MongoTransactionManager(mongoDbFactory);
//    }
}
