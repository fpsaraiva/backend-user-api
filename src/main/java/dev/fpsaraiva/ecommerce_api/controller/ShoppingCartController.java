package dev.fpsaraiva.ecommerce_api.controller;

import dev.fpsaraiva.ecommerce_api.dto.CartItemDto;
import dev.fpsaraiva.ecommerce_api.dto.ShoppingCartDto;
import dev.fpsaraiva.ecommerce_api.service.ShoppingCartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping
    public ResponseEntity<ShoppingCartDto> createCart() {
        ShoppingCartDto cart = shoppingCartService.createCart();
        return ResponseEntity.status(HttpStatus.CREATED).body(cart);
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<ShoppingCartDto> updateCart(
            @PathVariable UUID cartId,
            @Valid @RequestBody List<CartItemDto> cartItemDtos) {
        ShoppingCartDto updatedCart = shoppingCartService.updateCart(cartId, cartItemDtos);
        return ResponseEntity.ok(updatedCart);
    }
}
