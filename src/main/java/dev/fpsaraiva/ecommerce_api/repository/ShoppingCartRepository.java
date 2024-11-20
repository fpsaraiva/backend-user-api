package dev.fpsaraiva.ecommerce_api.repository;

import dev.fpsaraiva.ecommerce_api.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, UUID> {
}
