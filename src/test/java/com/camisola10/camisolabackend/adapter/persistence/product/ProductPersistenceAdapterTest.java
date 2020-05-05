package com.camisola10.camisolabackend.adapter.persistence.product;


import com.camisola10.camisolabackend.domain.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductPersistenceAdapterTest {

    @Mock
    private ProductRepository repository;
    @Mock
    private ProductDBMapper mapper;

    private ProductPersistenceAdapter adapter;

    @BeforeEach
    public void setUp() {
        adapter = new ProductPersistenceAdapter(repository, mapper);
    }

    @Test
    public void shouldReturnAllProducts() {
        var p1 = mock(ProductDb.class);
        var p2 = mock(ProductDb.class);
        when(repository.findAll()).thenReturn(List.of(p1, p2));

        List<Product> products = adapter.findAll();

        assertThat(products).hasSize(2);

        verify(mapper,times(2)).map(any(ProductDb.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldSaveProduct() {
        var productMock = mock(Product.class);
        var productDbMock = mock(ProductDb.class);
        when(mapper.map(productMock)).thenReturn(productDbMock);

        adapter.save(productMock);

        verify(mapper).map(productMock);
        verify(repository).save(productDbMock);
        verifyNoMoreInteractions(mapper);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldDeleteById() {
        var pId = mock(Product.ProductId.class);
        var id = "123";
        when(pId.asString()).thenReturn(id);

        adapter.deleteById(pId);

        verify(repository).deleteById(id);
        verifyNoMoreInteractions(repository);
    }
}
