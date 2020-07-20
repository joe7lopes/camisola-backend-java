package com.camisola10.camisolabackend.application.service;

import com.camisola10.camisolabackend.application.port.in.ImagesCommandService;
import com.camisola10.camisolabackend.application.port.in.ImagesQueryService;
import com.camisola10.camisolabackend.application.port.in.command.image.DeleteImagesCommand;
import com.camisola10.camisolabackend.application.port.in.command.image.UploadImagesCommand;
import com.camisola10.camisolabackend.application.port.out.CloudStorage;
import com.camisola10.camisolabackend.domain.images.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class ImageService implements ImagesCommandService, ImagesQueryService {

    private final CloudStorage cloudStorage;


    @Override
    public List<Image> getAll() {
        return cloudStorage.getAllImages();
    }

    @Override
    public List<Image> uploadImages(UploadImagesCommand command) {
       return cloudStorage.store(command.getImages());
    }

    @Override
    public void delete(DeleteImagesCommand command) {
        cloudStorage.deleteImages(command.getImagesIds());
    }


}
