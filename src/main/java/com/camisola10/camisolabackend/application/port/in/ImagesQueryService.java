package com.camisola10.camisolabackend.application.port.in;

import com.camisola10.camisolabackend.domain.images.Image;

import java.util.List;

import static com.camisola10.camisolabackend.domain.images.Image.ImageId;

public interface ImagesQueryService {

    List<Image> getAll();

    List<Image> findImagesById(List<ImageId> images);
}
