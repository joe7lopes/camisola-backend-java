package com.camisola10.camisolabackend.rest.images;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
class UploadImageRequest {
    private String name;
    //base 64
    private String file;
}
