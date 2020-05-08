package com.camisola10.camisolabackend.adapter.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class S3Config {

    @Bean
    AmazonS3 s3Client(S3Properties properties) {
        var credentials = new BasicAWSCredentials(properties.getAccessKey(), properties.getAccessKeySecret());
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(properties.getRegion())
                .build();
    }

}
