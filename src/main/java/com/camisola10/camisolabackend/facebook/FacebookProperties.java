package com.camisola10.camisolabackend.facebook;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("fb")
@Getter
@Setter
public class FacebookProperties {
    private String api;
    private String pageId;
    private String userId;
    private String appId; //clientId
    private String appSecret; //clientSecret
}
