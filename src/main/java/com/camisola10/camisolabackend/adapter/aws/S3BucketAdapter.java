package com.camisola10.camisolabackend.adapter.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.camisola10.camisolabackend.application.port.in.command.product.CreateProductCommand;
import com.camisola10.camisolabackend.application.port.out.CloudStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.CacheControl;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.time.Duration;

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

        var bucketPath = s3Properties.getBucketName() + "/images";
        s3Client.putObject(bucketPath, image.getName(), fis, metadata);
        s3Client.setObjectAcl(bucketPath, image.getName(), PublicRead);
        log.info("Image {} uploaded successfully to {}", image.getName(), bucketPath);
        return String.format("https://%s.s3-%s.amazonaws.com/images/%s",
                s3Properties.getBucketName(), s3Properties.getRegion(), image.getName());
    }
}


