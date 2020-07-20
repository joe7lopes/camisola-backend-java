package com.camisola10.camisolabackend.application.port.in;

import com.camisola10.camisolabackend.domain.images.Image;

import java.util.List;

public interface ImagesQueryService {

    List<Image> getAll();
}
