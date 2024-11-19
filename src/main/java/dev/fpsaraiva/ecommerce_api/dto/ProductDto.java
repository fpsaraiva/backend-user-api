package dev.fpsaraiva.ecommerce_api.dto;

import dev.fpsaraiva.ecommerce_api.model.Product;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ProductDto(
        @NotBlank String sku,
        @NotBlank String name,
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0.") Float price,
        @Min(value = 0, message = "Product stock quantity cannot be less than 0.") int stockQuantity
) {
    public static ProductDto toDto(Product product) {
        return new ProductDto(
                product.getSku(),
                product.getName(),
                product.getPrice(),
                product.getStockQuantity()
        );
    }
}
