package dev.fpsaraiva.ecommerce_api.service;

import dev.fpsaraiva.ecommerce_api.dto.ProductDto;
import dev.fpsaraiva.ecommerce_api.exceptions.DuplicateSkuException;
import dev.fpsaraiva.ecommerce_api.model.Product;
import dev.fpsaraiva.ecommerce_api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Page<ProductDto> getAll(Pageable pageable) {
        Page<Product> productsPage = productRepository.findByStockQuantityGreaterThan(0, pageable);
        return productsPage
                .map(product -> new ProductDto(
                        product.getSku(),
                        product.getName(),
                        product.getPrice(),
                        product.getStockQuantity()
                ));
    }

    public ProductDto save(ProductDto productDto) {
        if (productRepository.existsBySku(productDto.sku())) {
            throw new DuplicateSkuException("A product with SKU '" + productDto.sku() + "' already exists.");
        }
        Product product = productRepository.save(Product.toModel(productDto));
        return ProductDto.toDto(product);
    }

    public ProductDto update(String sku, ProductDto productDto) {
        Product product = productRepository.findBySku(sku);

        product.setName(productDto.name());
        product.setPrice(productDto.price());
        product.setStockQuantity(productDto.stockQuantity());
        productRepository.save(Product.toModel(productDto));

        return ProductDto.toDto(product);
    }
}
