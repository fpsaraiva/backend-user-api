package dev.fpsaraiva.ecommerce_api.dto;

import dev.fpsaraiva.ecommerce_api.model.CartItem;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CartItemDto(
        @NotNull UUID productId,
        @Min(value = 0, message = "Quantity must be greater than 0.") int quantity
) {
    public static CartItemDto toDto(CartItem cartItem) {
        return new CartItemDto(
                cartItem.getProduct().getId(),
                cartItem.getQuantity()
        );
    }
}