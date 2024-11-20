package dev.fpsaraiva.ecommerce_api.service;

import dev.fpsaraiva.ecommerce_api.dto.ShoppingCartDto;
import dev.fpsaraiva.ecommerce_api.model.ShoppingCart;
import dev.fpsaraiva.ecommerce_api.model.User;
import dev.fpsaraiva.ecommerce_api.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {

    @Autowired
    private AuthService authService;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartDto createCart() {
        User user = authService.getAuthenticatedUser();

        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);

        shoppingCartRepository.save(cart);
        return ShoppingCartDto.toDto(cart);
    }
}
