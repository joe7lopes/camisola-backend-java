package com.camisola10.camisolabackend.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.camisola10.camisolabackend.application.port.in.command.product.Base64Image;
import com.camisola10.camisolabackend.application.port.out.CloudStorage;
import com.camisola10.camisolabackend.domain.images.Image;
import com.camisola10.camisolabackend.domain.images.Image.ImageId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.CacheControl;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.amazonaws.services.s3.model.CannedAccessControlList.PublicRead;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.stream.Collectors.toUnmodifiableList;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("!local")
class S3BucketAdapter implements CloudStorage {

    private final AmazonS3 s3Client;
    private final S3Properties s3Properties;

    @Override
    public List<Image> getAllImages() {
        var request = new ListObjectsRequest()
                .withBucketName(s3Properties.getBucketName())
                .withPrefix(s3Properties.getBucketPath() + "/")
                .withMarker(s3Properties.getBucketPath() + "/");

        return s3Client.listObjects(request).
                getObjectSummaries().stream()
                .map(storedObject -> {
                    var key = storedObject.getKey();
                    var myKey = key.replace(s3Properties.getBucketPath() + "/", "");
                    var url = String.format("https://%s.s3-%s.amazonaws.com/%s/%s", s3Properties.getBucketName(), s3Properties.getRegion(), s3Properties.getBucketPath(), myKey);
                    return Image.builder()
                            .id(ImageId.createFrom(myKey))
                            .name(myKey)
                            .url(url)
                            .lastModified(storedObject.getLastModified())
                            .build();
                }).collect(toUnmodifiableList());
    }

    @Override
    public List<Image> getImagesByIds(List<ImageId> imageIds) {
        return imageIds.stream()
                .map(id -> String.format("%s/%s",s3Properties.getBucketPath(),id.asString()))
                .map(s3Key -> s3Client.getObject(s3Properties.getBucketName(), s3Key))
                .map(storedObject -> {
                    var key = storedObject.getKey();
                    var myKey = key.replace(s3Properties.getBucketPath() + "/", "");
                    var url = String.format("https://%s.s3-%s.amazonaws.com/%s/%s", s3Properties.getBucketName(), s3Properties.getRegion(), s3Properties.getBucketPath(), myKey);
                    return Image.builder()
                            .id(ImageId.createFrom(myKey))
                            .name(myKey)
                            .url(url)
                            .lastModified(storedObject.getObjectMetadata().getLastModified())
                            .build();
                }).collect(toUnmodifiableList());
    }

    @Override
    public Image store(Base64Image image) {
        var imageId = ImageId.create();
        var imageIdStr = imageId.asString();
        var content = image.getFile();
        var fis = new ByteArrayInputStream(content);
        var metadata = getObjectMetadata(image, imageIdStr, content);

        var bucketName = s3Properties.getBucketName();
        var bucketPath = s3Properties.getBucketPath();
        var filePath = bucketName + "/" + bucketPath;
        s3Client.putObject(filePath, imageIdStr, fis, metadata);
        s3Client.setObjectAcl(filePath, imageIdStr, PublicRead);
        log.info("Image {} uploaded successfully to {}", imageIdStr, filePath);

        var url = String.format("https://%s.s3-%s.amazonaws.com/%s/%s", bucketName, s3Properties.getRegion(), bucketPath, imageIdStr);
        return Image.builder()
                .id(imageId)
                .name(image.getName())
                .url(url)
                .lastModified(new Date())
                .build();
    }

    @Override
    public List<Image> store(List<Base64Image> images) {
        return images.stream()
                .map(this::store)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteImages(List<ImageId> imagesKeys) {
        imagesKeys.forEach(key -> s3Client.deleteObject(
                s3Properties.getBucketName(),
                s3Properties.getBucketPath() + "/" + key.asString()));
    }

    private ObjectMetadata getObjectMetadata(Base64Image image, String imageIdStr, byte[] content) {
        Map<String, String> userMetaData = Map.of("id", imageIdStr, "name", image.getNameAsUTF8());
        var metadata = new ObjectMetadata();
        metadata.setUserMetadata(userMetaData);
        metadata.setContentLength(content.length);
        metadata.setContentType(image.getContentType().getMimeType());
        metadata.setCacheControl(
                CacheControl.maxAge(Duration.of(30, DAYS))
                        .cachePublic()
                        .getHeaderValue()
        );
        return metadata;
    }

}


