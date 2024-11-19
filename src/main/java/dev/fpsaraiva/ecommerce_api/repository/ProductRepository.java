package dev.fpsaraiva.ecommerce_api.repository;

import dev.fpsaraiva.ecommerce_api.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByStockQuantityGreaterThan(int stockQuantity, Pageable pageable);

    Product findBySku(String sku);

    boolean existsBySku(String sku);
}
