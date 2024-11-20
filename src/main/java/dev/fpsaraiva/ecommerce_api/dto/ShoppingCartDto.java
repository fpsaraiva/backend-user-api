package dev.fpsaraiva.ecommerce_api.dto;

import dev.fpsaraiva.ecommerce_api.model.ShoppingCart;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record ShoppingCartDto(
        @NotBlank UUID userId
        ) {
    public static ShoppingCartDto toDto(ShoppingCart shoppingCart) {
        return new ShoppingCartDto(
                shoppingCart.getUser().getId()
        );
    }
}
