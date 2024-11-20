package dev.fpsaraiva.ecommerce_api.dto;

import dev.fpsaraiva.ecommerce_api.model.ShoppingCart;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

public record ShoppingCartDto(
        @NotBlank UUID userId,
        List<CartItemDto> products
        ) {
    public static ShoppingCartDto toDto(ShoppingCart shoppingCart) {
        return new ShoppingCartDto(
                shoppingCart.getUser().getId(),
                shoppingCart.getProducts().stream()
                        .map(CartItemDto::toDto)
                        .toList()
        );
    }
}
