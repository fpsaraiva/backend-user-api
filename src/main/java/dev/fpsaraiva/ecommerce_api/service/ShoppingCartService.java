package dev.fpsaraiva.ecommerce_api.service;

import dev.fpsaraiva.ecommerce_api.dto.CartItemDto;
import dev.fpsaraiva.ecommerce_api.dto.ShoppingCartDto;
import dev.fpsaraiva.ecommerce_api.exceptions.InsufficientAvailableStockException;
import dev.fpsaraiva.ecommerce_api.exceptions.ResourceNotFoundException;
import dev.fpsaraiva.ecommerce_api.exceptions.UnauthorizedAccessException;
import dev.fpsaraiva.ecommerce_api.model.CartItem;
import dev.fpsaraiva.ecommerce_api.model.Product;
import dev.fpsaraiva.ecommerce_api.model.ShoppingCart;
import dev.fpsaraiva.ecommerce_api.model.User;
import dev.fpsaraiva.ecommerce_api.repository.ProductRepository;
import dev.fpsaraiva.ecommerce_api.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ShoppingCartService {

    @Autowired
    private AuthService authService;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ProductRepository productRepository;

    public ShoppingCartDto createCart() {
        User user = authService.getAuthenticatedUser();

        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);

        shoppingCartRepository.save(cart);
        return ShoppingCartDto.toDto(cart);
    }

    public ShoppingCartDto updateCart(UUID cartId, List<CartItemDto> cartItemDtos) {
        UUID authenticatedUserId = authService.getAuthenticatedUser().getId();

        ShoppingCart shoppingCart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with ID: " + cartId));

        if (!shoppingCart.getUser().getId().equals(authenticatedUserId)) {
            throw new UnauthorizedAccessException("Logged user is not allowed to edit this cart.");
        }

        shoppingCart.getProducts().clear();
        for (CartItemDto cartItemDto : cartItemDtos) {
            Product product = productRepository.findById(cartItemDto.productId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product with ID '" + cartItemDto.productId() + "' was not found."));

            if (cartItemDto.quantity() > product.getStockQuantity()) {
                throw new InsufficientAvailableStockException(
                        "Quantity of product '" + cartItemDto.productId() + "' exceeds available stock."
                );
            }

            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(cartItemDto.quantity());
            cartItem.setShoppingCart(shoppingCart);

            shoppingCart.getProducts().add(cartItem);

            int updatedProductStockQuantity = product.getStockQuantity() - cartItemDto.quantity();
            product.setStockQuantity(updatedProductStockQuantity);
        }

        ShoppingCart updatedCart = shoppingCartRepository.save(shoppingCart);

        return ShoppingCartDto.toDto(updatedCart);
    }

}
