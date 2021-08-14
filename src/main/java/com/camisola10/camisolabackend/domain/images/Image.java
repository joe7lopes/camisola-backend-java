package com.camisola10.camisolabackend.domain.images;

import lombok.Builder;
import lombok.Value;

import java.util.Date;
import java.util.UUID;

@Value
@Builder
public class Image {
    ImageId id;
    String name;
    String url;
    Date lastModified;

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
