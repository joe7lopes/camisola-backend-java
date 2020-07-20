package com.camisola10.camisolabackend.application.port.in.command.image;

import com.camisola10.camisolabackend.application.port.in.command.product.Base64Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UploadImagesCommand {
    List<Base64Image> images;
}
