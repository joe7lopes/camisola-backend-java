package com.camisola10.camisolabackend.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.camisola10.camisolabackend.application.port.in.command.product.Base64Image;
import com.camisola10.camisolabackend.application.port.out.CloudStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.util.List;

import static org.apache.http.entity.ContentType.IMAGE_PNG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class S3BucketAdapterTest {

    @Mock
    private AmazonS3 s3Client;
    @Mock
    private S3Properties properties;
    private CloudStorage adapter;

    @BeforeEach
    public void setUp() {
        adapter = new S3BucketAdapter(s3Client, properties);
    }

    @Test
    public void shouldStoreImageAndReturnUrl() {
        var bucketName = "bucketName";
        var region = "region";
        var image = Base64Image.builder()
                .name("img1")
                .file(new byte[]{})
                .contentType(IMAGE_PNG)
                .build();
        when(properties.getBucketName()).thenReturn(bucketName);
        when(properties.getRegion()).thenReturn(region);
        when(properties.getBucketPath()).thenReturn("images");

        var response = adapter.store(image);

        verify(s3Client).putObject(eq(bucketName + "/images"), any(String.class), any(InputStream.class), any(ObjectMetadata.class));
        assertThat(response.getUrl()).isEqualTo("https://bucketName.s3-region.amazonaws.com/images/" + response.getId().asString());

    }

    @Test
    public void shouldRemoveImages() {
        var bucketName = "bucketName";
        var bucketPath = "images";
        var id1 = com.camisola10.camisolabackend.domain.images.Image.ImageId.create();
        var id2 = com.camisola10.camisolabackend.domain.images.Image.ImageId.create();
        var id3 = com.camisola10.camisolabackend.domain.images.Image.ImageId.create();
        var imageIds = List.of(id1, id2, id3);
        when(properties.getBucketName()).thenReturn(bucketName);
        when(properties.getBucketPath()).thenReturn(bucketPath);

        adapter.deleteImages(imageIds);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(s3Client, times(3)).deleteObject(eq(bucketName), captor.capture());
        assertThat(captor.getAllValues()).containsExactly("images/"+id1.asString(), "images/"+id2.asString(), "images/"+id3.asString());
    }

}
