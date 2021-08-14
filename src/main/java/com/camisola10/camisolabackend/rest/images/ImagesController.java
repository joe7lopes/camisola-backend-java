package com.camisola10.camisolabackend.rest.images;

import com.camisola10.camisolabackend.application.port.in.ImagesCommandService;
import com.camisola10.camisolabackend.application.port.in.ImagesQueryService;
import com.camisola10.camisolabackend.application.port.in.command.image.DeleteImagesCommand;
import com.camisola10.camisolabackend.application.port.in.command.image.UploadImagesCommand;
import com.camisola10.camisolabackend.rest.ApiUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiUrl.IMAGES)
class ImagesController {

    private final ImagesCommandService imagesCommandService;
    private final ImagesQueryService imagesQueryService;
    private final ImagesMapper mapper;

    @GetMapping
    List<ImageResponse> getImages(){
        return imagesQueryService.getAll().stream()
                .map(ImageResponse::new)
                .collect(toUnmodifiableList());
    }

    @PostMapping
    List<ImageResponse> uploadImages(@RequestBody List<UploadImageRequest> uploadImageRequests){
        UploadImagesCommand command = mapper.map(uploadImageRequests);
        return imagesCommandService.uploadImages(command).stream()
                .map(ImageResponse::new)
                .collect(toUnmodifiableList());
    }

    @DeleteMapping
    void deleteImages(@RequestBody List<String> imageIds){
        var command = new DeleteImagesCommand(mapper.mapImageIds(imageIds));
        imagesCommandService.delete(command);
    }

}
