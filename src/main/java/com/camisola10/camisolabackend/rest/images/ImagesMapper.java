package com.camisola10.camisolabackend.rest.images;

import com.camisola10.camisolabackend.application.port.in.command.image.UploadImagesCommand;
import com.camisola10.camisolabackend.application.port.in.command.product.Base64Image;
import com.camisola10.camisolabackend.domain.images.Image.ImageId;
import org.apache.http.entity.ContentType;
import org.mapstruct.Mapper;

import java.util.Base64;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Mapper(componentModel = "spring")
interface ImagesMapper {

    List<ImageId> mapImageIds(List<String> imageId);

    default ImageId mapFromId(String imageId){
        return ImageId.createFrom(imageId);
    }

    default UploadImagesCommand map(List<UploadImageRequest> request) {
        return new UploadImagesCommand(request.stream().map(this::map).collect(toList()));
    }

    default Base64Image map(UploadImageRequest request) {
        var parts = request.getFile().split("[:;,]");
        var contentType = ContentType.create(parts[1]);
        var bytes = Base64.getDecoder().decode(parts[3]);
        return Base64Image.builder()
                .name(request.getName())
                .file(bytes)
                .contentType(contentType)
                .build();
    }
}
