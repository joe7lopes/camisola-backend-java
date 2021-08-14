package com.camisola10.camisolabackend.rest.images;

import com.camisola10.camisolabackend.domain.images.Image;
import lombok.Data;

import java.util.Date;

@Data
class ImageResponse {
    String id;
    String url;
    Date lastModified;

    public ImageResponse(Image image) {
        this.id = image.getId().asString();
        this.url = image.getUrl();
        this.lastModified = image.getLastModified();
    }
}
