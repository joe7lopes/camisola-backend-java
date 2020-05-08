package com.camisola10.camisolabackend.adapter.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.camisola10.camisolabackend.application.port.in.command.product.CreateProductCommand;
import com.camisola10.camisolabackend.application.port.out.CloudStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;

import static org.apache.http.entity.ContentType.IMAGE_PNG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
        var image = new CreateProductCommand.Base64Image("img1", new byte[]{}, IMAGE_PNG, false);
        when(properties.getBucketName()).thenReturn(bucketName);
        when(properties.getRegion()).thenReturn(region);
        var url = adapter.store(image);

        verify(s3Client).putObject(eq(bucketName +"/images"), eq("img1"), any(InputStream.class), any(ObjectMetadata.class));

        assertThat(url).isEqualTo("https://bucketName.s3-region.amazonaws.com/images/img1");
    }

}
