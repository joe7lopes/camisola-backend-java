package com.camisola10.camisolabackend.adapter.rest.images;

import com.camisola10.camisolabackend.adapter.rest.ApiUrl;
import com.camisola10.camisolabackend.application.port.in.ImagesCommandService;
import com.camisola10.camisolabackend.application.port.in.ImagesQueryService;
import com.camisola10.camisolabackend.application.port.in.command.image.DeleteImagesCommand;
import com.camisola10.camisolabackend.application.port.in.command.image.UploadImagesCommand;
import com.camisola10.camisolabackend.domain.images.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiUrl.IMAGES)
class ImagesController {

    private final ImagesCommandService imagesCommandService;
    private final ImagesQueryService imagesQueryService;
    private final ImagesMapper mapper;

    @GetMapping
    List<Image> getImages(){
        return imagesQueryService.getAll();
    }

    @PostMapping
    List<Image> uploadImages(@RequestBody List<UploadImageRequest> uploadImageRequests){
        UploadImagesCommand command = mapper.map(uploadImageRequests);
        return imagesCommandService.uploadImages(command);
    }

    @DeleteMapping
    void deleteImages(@RequestBody List<String> imageIds){
        var command = new DeleteImagesCommand(mapper.mapImageIds(imageIds));
        imagesCommandService.delete(command);
    }

}
