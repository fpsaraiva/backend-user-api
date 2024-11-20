package dev.fpsaraiva.ecommerce_api.service;

import dev.fpsaraiva.ecommerce_api.dto.ProductDto;
import dev.fpsaraiva.ecommerce_api.exceptions.DuplicateSkuException;
import dev.fpsaraiva.ecommerce_api.exceptions.ResourceNotFoundException;
import dev.fpsaraiva.ecommerce_api.model.Product;
import dev.fpsaraiva.ecommerce_api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Page<ProductDto> listProducts(Pageable pageable) {
        Page<Product> productsPage = productRepository.findByStockQuantityGreaterThan(0, pageable);
        return productsPage
                .map(product -> new ProductDto(
                        product.getSku(),
                        product.getName(),
                        product.getPrice(),
                        product.getStockQuantity()
                ));
    }

    public ProductDto createProduct(ProductDto productDto) {
        if (productRepository.existsBySku(productDto.sku())) {
            throw new DuplicateSkuException("A product with SKU '" + productDto.sku() + "' already exists.");
        }
        Product product = productRepository.save(Product.toModel(productDto));
        return ProductDto.toDto(product);
    }

    public ProductDto updateProduct(UUID id, ProductDto productDto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));

        if (!existingProduct.getSku().equals(productDto.sku())) {
            throw new DuplicateSkuException("Cannot Update SKU. Product already registered with SKU " + existingProduct.getSku() + ".");
        }

        existingProduct.setName(productDto.name());
        existingProduct.setPrice(productDto.price());
        existingProduct.setStockQuantity(productDto.stockQuantity());

        Product updatedProduct = productRepository.save(existingProduct);

        return ProductDto.toDto(updatedProduct);
    }
}
