package com.camisola10.camisolabackend.domain.images;

import lombok.Value;

import java.util.UUID;

@Value
public class Image {
    ImageId id;
    String name;
    String url;

    @Value
    public static class ImageId {
        UUID value;

        public static ImageId create(){
            return new ImageId(UUID.randomUUID());
        }

        public static ImageId createFrom(String uuId){
            return new ImageId(UUID.fromString(uuId));
        }

        public String asString(){
            return value.toString();
        }
    }
}
