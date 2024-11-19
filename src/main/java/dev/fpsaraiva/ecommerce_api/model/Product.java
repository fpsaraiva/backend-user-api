package dev.fpsaraiva.ecommerce_api.model;

import dev.fpsaraiva.ecommerce_api.dto.ProductDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String sku;
    private String name;
    private float price;
    private int stockQuantity;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public static Product toModel(ProductDto productDto) {
        Product product = new Product();
        product.setSku(productDto.sku());
        product.setName(productDto.name());
        product.setPrice(productDto.price());
        product.setStockQuantity(productDto.stockQuantity());
        return product;
    }
}
