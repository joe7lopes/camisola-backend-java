package com.camisola10.camisolabackend.application.port.in.command.product;

import lombok.Builder;
import lombok.Value;
import org.apache.http.entity.ContentType;

@Value
@Builder
public class Base64Image {
    String name;
    byte[] file;
    ContentType contentType;

    public static Base64Image create(String name, byte[] file, ContentType contentType) {
        return Base64Image.builder()
                .name(name)
                .file(file)
                .contentType(contentType)
                .build();
    }
}
