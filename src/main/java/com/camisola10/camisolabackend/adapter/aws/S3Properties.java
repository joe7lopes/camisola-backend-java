package com.camisola10.camisolabackend.adapter.aws;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("aws")
@Getter
@Setter
public class S3Properties {
    private String accessKey;
    private String accessKeySecret;
    private String bucketName;
    private String region;
    private String bucketPath;
}
