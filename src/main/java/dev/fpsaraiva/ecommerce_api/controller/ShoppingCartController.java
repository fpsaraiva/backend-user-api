package dev.fpsaraiva.ecommerce_api.controller;

import dev.fpsaraiva.ecommerce_api.dto.ShoppingCartDto;
import dev.fpsaraiva.ecommerce_api.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
