package dev.fpsaraiva.ecommerce_api.controller;

import dev.fpsaraiva.ecommerce_api.dto.ProductDto;
import dev.fpsaraiva.ecommerce_api.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product")
    public Page<ProductDto> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productService.getAll(pageable);
    }

    @PostMapping("/product")
    public ProductDto createProduct(@Valid @RequestBody ProductDto productDto) {
        return productService.createProduct(productDto);
    }

    @PutMapping("/product/{id}")
    public ProductDto updateProduct(
            @PathVariable UUID id,
            @Valid @RequestBody ProductDto productDto) {
        return productService.updateProduct(id, productDto);
    }
}
