package dev.fpsaraiva.ecommerce_api.repository;

import dev.fpsaraiva.ecommerce_api.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Page<Product> findByStockQuantityGreaterThan(int stockQuantity, Pageable pageable);

    boolean existsBySku(String sku);
}
