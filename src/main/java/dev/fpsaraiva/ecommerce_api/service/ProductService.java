package dev.fpsaraiva.ecommerce_api.service;

import dev.fpsaraiva.ecommerce_api.dto.ProductDto;
import dev.fpsaraiva.ecommerce_api.model.Product;
import dev.fpsaraiva.ecommerce_api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Product product = productRepository.save(Product.toModel(productDto));
        return ProductDto.toDto(product);
    }

    public ProductDto update(String sku, ProductDto productDto) {
        Product product = productRepository.findBySku(sku);

        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setStockQuantity(productDto.getStockQuantity());
        productRepository.save(Product.toModel(productDto));

        return ProductDto.toDto(product);
    }

    public ProductDto delete(long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()) {
            productRepository.delete(product.get());
        }
        return null;
    }
}
