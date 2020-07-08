package com.camisola10.camisolabackend.adapter.aws;

import com.camisola10.camisolabackend.application.port.in.command.product.CreateProductCommand;
import com.camisola10.camisolabackend.application.port.out.CloudStorage;
import com.camisola10.camisolabackend.domain.product.ProductImage;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("local")
class S3BucketAdapterMock implements CloudStorage {
    @Override
    public String store(CreateProductCommand.Base64Image image) {
        return "https://camisola-backend.s3-eu-west-1.amazonaws.com/images/defaultshirt.png";
    }

    @Override
    public void removeImages(List<ProductImage> images) {
    }
}
