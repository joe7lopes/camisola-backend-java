package com.camisola10.camisolabackend.adapter.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.camisola10.camisolabackend.application.port.in.command.product.CreateProductCommand;
import com.camisola10.camisolabackend.application.port.out.CloudStorage;
import com.camisola10.camisolabackend.domain.product.ProductImage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.CacheControl;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.util.List;

import static com.amazonaws.services.s3.model.CannedAccessControlList.PublicRead;
import static java.time.temporal.ChronoUnit.DAYS;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("!local")
class S3BucketAdapter implements CloudStorage {

    private final AmazonS3 s3Client;
    private final S3Properties s3Properties;

    @Override
    public String store(CreateProductCommand.Base64Image image) {
        var content = image.getFile();
        var fis = new ByteArrayInputStream(content);
        var metadata = new ObjectMetadata();
        metadata.setContentLength(content.length);
        metadata.setContentType(image.getContentType().getMimeType());
        metadata.setCacheControl(
                CacheControl.maxAge(Duration.of(30, DAYS))
                        .cachePublic()
                        .getHeaderValue()
        );


        var bucketName = s3Properties.getBucketName();
        var bucketPath = s3Properties.getBucketPath();
        var filePath = bucketName + "/" + bucketPath;
        s3Client.putObject(filePath, image.getName(), fis, metadata);
        s3Client.setObjectAcl(filePath, image.getName(), PublicRead);
        log.info("Image {} uploaded successfully to {}", image.getName(), filePath);
        return String.format("https://%s.s3-%s.amazonaws.com/%s/%s",
                bucketName, s3Properties.getRegion(), bucketPath, image.getName());
    }

    @Override
    public void removeImages(List<ProductImage> images) {
        var filePath = String.format("%s/%s", s3Properties.getBucketName(), s3Properties.getBucketPath());
        images.stream()
                .map(ProductImage::getName)
                .forEach(key-> s3Client.deleteObject(filePath, key));
    }
}


