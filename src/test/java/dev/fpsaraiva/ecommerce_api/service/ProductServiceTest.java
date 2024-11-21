package dev.fpsaraiva.ecommerce_api.service;

import dev.fpsaraiva.ecommerce_api.dto.ProductDto;
import dev.fpsaraiva.ecommerce_api.exceptions.DuplicateSkuException;
import dev.fpsaraiva.ecommerce_api.model.Product;
import dev.fpsaraiva.ecommerce_api.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldCreateProductWhenSkuDoesNotExist() {
        ProductDto productDto = new ProductDto("SKU123", "Product Name", 100f, 1);
        Product savedProduct = new Product("SKU123", "Product Name", 100f, 1);

        when(productRepository.existsBySku(productDto.sku())).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        ProductDto result = productService.createProduct(productDto);

        assertNotNull(result);
        assertEquals(productDto.sku(), result.sku());
        assertEquals(productDto.name(), result.name());
        assertEquals(productDto.price(), result.price());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    public void shouldThrowExceptionWhenSkuAlreadyExists() {
        ProductDto productDto = new ProductDto("SKU123", "Product Name", 100.0f, 1);

        when(productRepository.existsBySku(productDto.sku())).thenReturn(true);

        Exception exception = assertThrows(DuplicateSkuException.class, () -> {
            productService.createProduct(productDto);
        });

        assertEquals("A product with SKU 'SKU123' already exists.", exception.getMessage());
        verify(productRepository, never()).save(any(Product.class));
    }
}
