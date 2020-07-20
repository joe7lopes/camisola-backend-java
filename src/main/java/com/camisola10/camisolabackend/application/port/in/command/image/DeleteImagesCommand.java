package com.camisola10.camisolabackend.application.port.in.command.image;

import com.camisola10.camisolabackend.domain.images.Image.ImageId;
import lombok.Value;

import java.util.List;

@Value
public class DeleteImagesCommand {
    List<ImageId> imagesIds;
}
