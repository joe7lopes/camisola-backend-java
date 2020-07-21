//package com.camisola10.camisolabackend.application.service;
//
//import com.camisola10.camisolabackend.application.port.in.ImagesQueryService;
//import com.camisola10.camisolabackend.application.port.in.command.product.Base64Image;
//import com.camisola10.camisolabackend.application.port.in.command.product.CreateProductCommand;
//import com.camisola10.camisolabackend.application.port.in.command.product.RemoveProductCommand;
//import com.camisola10.camisolabackend.application.port.out.CloudStorage;
//import com.camisola10.camisolabackend.application.port.out.ProductDB;
//import com.camisola10.camisolabackend.domain.images.Image;
//import com.camisola10.camisolabackend.domain.product.Product;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.verifyNoMoreInteractions;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class ProductServiceTest {
////TODO: fix tests
//
//    @Mock
//    private ImagesQueryService imagesQueryService;
//    @Mock
//    private ProductDB db;
//
//    private ProductService productService;
//
//    @BeforeEach
//    public void setUp() {
//        productService = new ProductService(imagesQueryService, db);
//    }
//
//    @Test
//    public void shouldRetrieveAllProducts() {
//        var p1 = mock(Product.class);
//        var p2 = mock(Product.class);
//        var productsMock = Arrays.asList(p1, p2);
//        when(db.findAll()).thenReturn(productsMock);
//
//        List<Product> products = productService.getAll();
//
//        assertThat(products).hasSize(2);
//    }
//
//    @Test
//    public void shouldFindById() {
//
//        Product.ProductId id = Product.ProductId.create();
//        productService.findProductById(id);
//
//        verify(db).findById(id);
//        verifyNoMoreInteractions(db);
//    }
//
//    @Test
//    public void shouldSaveProduct() {
//        var command = mock(CreateProductCommand.class);
//        var productMock = mock(Product.class);
//        when(mapper.map(command)).thenReturn(productMock);
//
//        var product = productService.createProduct(command);
//
//        assertThat(product).isEqualTo(productMock);
//        verify(db).save(productMock);
//        verifyNoMoreInteractions(db);
//    }
//
//    @Test
//    public void shouldUploadImagesToCloudAndGetURL() {
//        var img1Id = mock(Image.ImageId.class);
//        var img2Id = mock(Image.ImageId.class);
//
//        var img1 = new Image(img1Id, "img1", "img1.com");
//        var img2 = new Image(img2Id, "img2", "img2.com");
//
//
//        var command = mock(CreateProductCommand.class);
//        var product = Product.builder()
//                .id(Product.ProductId.create())
//                .name("p1")
//                .build();
//
//        when(command.getImages()).thenReturn(List.of(img1Id, img2Id));
//        when(mapper.map(command)).thenReturn(product);
//
//        Product expected = productService.createProduct(command);
//
//        assertThat(expected.getImages().get(0).getUrl()).isEqualTo("img1.com");
//        assertThat(expected.getImages().get(1).getUrl()).isEqualTo("img2.com");
//        verify(mapper).map(command);
//        verifyNoMoreInteractions(cloudStorage);
//        verifyNoMoreInteractions(mapper);
//    }
//
//    @Test
//    public void shouldRemoveProduct() {
//        var command = mock(RemoveProductCommand.class);
//        var id = mock(Product.ProductId.class);
//        var product = mock(Product.class);
//
//        when(command.getProductId()).thenReturn(id);
//        when(db.findById(id)).thenReturn(Optional.of(product));
//
//        productService.removeProduct(command);
//
//        verify(db).deleteById(id);
//        verifyNoMoreInteractions(cloudStorage);
//        verifyNoMoreInteractions(db);
//    }
//
//}
