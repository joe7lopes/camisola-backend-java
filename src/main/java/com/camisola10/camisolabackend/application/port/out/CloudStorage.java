package com.camisola10.camisolabackend.application.port.out;

import com.camisola10.camisolabackend.application.port.in.command.product.Base64Image;
import com.camisola10.camisolabackend.domain.images.Image;
import com.camisola10.camisolabackend.domain.images.Image.ImageId;

import java.util.List;

public interface CloudStorage {

    List<Image> getAllImages();

    List<Image> getImagesByIds(List<ImageId> imageIds);

    Image store(Base64Image image);

    List<Image> store(List<Base64Image> images);

    void deleteImages(List<ImageId> imagesKeys);

}