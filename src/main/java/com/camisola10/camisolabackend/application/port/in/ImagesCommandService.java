package com.camisola10.camisolabackend.application.port.in;

import com.camisola10.camisolabackend.application.port.in.command.image.DeleteImagesCommand;
import com.camisola10.camisolabackend.application.port.in.command.image.UploadImagesCommand;
import com.camisola10.camisolabackend.domain.images.Image;

import java.util.List;

public interface ImagesCommandService {
    List<Image> uploadImages(UploadImagesCommand command);

    void delete(DeleteImagesCommand command);
}
