package com.camisola10.camisolabackend.aws;

import com.camisola10.camisolabackend.application.port.in.command.product.Base64Image;
import com.camisola10.camisolabackend.application.port.out.CloudStorage;
import com.camisola10.camisolabackend.domain.images.Image;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("local")
class S3BucketAdapterMock implements CloudStorage {

    private static final Image MOCK_IMAGE = new Image(Image.ImageId.create(), "mockImage", "https://camisola-backend.s3-eu-west-1.amazonaws.com/defaultshirt.png");

    @Override
    public List<Image> getAllImages() {
        return List.of(MOCK_IMAGE);
    }

    @Override
    public List<Image> getImagesByIds(List<Image.ImageId> imageIds) {
        return List.of(MOCK_IMAGE);
    }

    @Override
    public Image store(Base64Image image) {
        return MOCK_IMAGE;
    }

    @Override
    public List<Image> store(List<Base64Image> image) {
        return List.of(MOCK_IMAGE);
    }

    @Override
    public void deleteImages(List<Image.ImageId> imagesKeys) {

    }
}
