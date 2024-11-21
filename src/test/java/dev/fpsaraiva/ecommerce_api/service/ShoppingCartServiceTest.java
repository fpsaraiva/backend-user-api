package dev.fpsaraiva.ecommerce_api.service;

import dev.fpsaraiva.ecommerce_api.dto.CartItemDto;
import dev.fpsaraiva.ecommerce_api.dto.ShoppingCartDto;
import dev.fpsaraiva.ecommerce_api.exceptions.InsufficientAvailableStockException;
import dev.fpsaraiva.ecommerce_api.exceptions.UnauthorizedAccessException;
import dev.fpsaraiva.ecommerce_api.model.Product;
import dev.fpsaraiva.ecommerce_api.model.ShoppingCart;
import dev.fpsaraiva.ecommerce_api.model.User;
import dev.fpsaraiva.ecommerce_api.repository.ProductRepository;
import dev.fpsaraiva.ecommerce_api.repository.ShoppingCartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ShoppingCartServiceTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private AuthService authService;

    @InjectMocks
    private ShoppingCartService shoppingCartService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldCreateCartSuccessfully() {
        User user = new User();
        user.setId(UUID.randomUUID());

        when(authService.getAuthenticatedUser()).thenReturn(user);

        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);

        when(shoppingCartRepository.save(any(ShoppingCart.class))).thenReturn(cart);

        ShoppingCartDto result = shoppingCartService.createCart();

        assertNotNull(result);
        assertEquals(user.getId(), result.userId());
        verify(shoppingCartRepository).save(any(ShoppingCart.class));
    }

    @Test
    public void shouldUpdateCartSuccessfully() {
        UUID cartId = UUID.randomUUID();
        UUID authenticatedUserId = UUID.randomUUID();

        User user = new User();
        user.setId(authenticatedUserId);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(cartId);
        shoppingCart.setUser(user);

        CartItemDto cartItemDto = new CartItemDto(UUID.randomUUID(), 2);
        Product product = new Product();
        product.setId(cartItemDto.productId());
        product.setStockQuantity(10);

        when(authService.getAuthenticatedUser()).thenReturn(user);
        when(shoppingCartRepository.findById(cartId)).thenReturn(Optional.of(shoppingCart));
        when(productRepository.findById(cartItemDto.productId())).thenReturn(Optional.of(product));
        when(shoppingCartRepository.save(any(ShoppingCart.class))).thenReturn(shoppingCart);

        ShoppingCartDto result = shoppingCartService.updateCart(cartId, List.of(cartItemDto));

        assertNotNull(result);
        assertEquals(cartId, result.cartId());
        assertEquals(1, shoppingCart.getProducts().size());
        assertEquals(8, product.getStockQuantity());
        verify(shoppingCartRepository).save(any(ShoppingCart.class));
    }

    @Test
    public void shouldThrowExceptionWhenUpdatingCartWithInsufficientStock() {
        UUID cartId = UUID.randomUUID();
        UUID authenticatedUserId = UUID.randomUUID();

        User user = new User();
        user.setId(authenticatedUserId);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(cartId);
        shoppingCart.setUser(user);

        CartItemDto cartItemDto = new CartItemDto(UUID.randomUUID(), 15);
        Product product = new Product();
        product.setId(cartItemDto.productId());
        product.setStockQuantity(10);

        when(authService.getAuthenticatedUser()).thenReturn(user);
        when(shoppingCartRepository.findById(cartId)).thenReturn(Optional.of(shoppingCart));
        when(productRepository.findById(cartItemDto.productId())).thenReturn(Optional.of(product));

        Exception exception = assertThrows(InsufficientAvailableStockException.class, () ->
                shoppingCartService.updateCart(cartId, List.of(cartItemDto))
        );

        assertEquals("Quantity of product '" + cartItemDto.productId() + "' exceeds available stock.", exception.getMessage());
        verify(shoppingCartRepository, never()).save(any(ShoppingCart.class));
    }

    @Test
    public void shouldGetShoppingCartByIdSuccessfully() {
        UUID cartId = UUID.randomUUID();
        UUID authenticatedUserId = UUID.randomUUID();

        User user = new User();
        user.setId(authenticatedUserId);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(cartId);
        shoppingCart.setUser(user);

        when(authService.getAuthenticatedUser()).thenReturn(user);
        when(shoppingCartRepository.findById(cartId)).thenReturn(Optional.of(shoppingCart));

        ShoppingCartDto result = shoppingCartService.getShoppingCartById(cartId);

        assertNotNull(result);
        assertEquals(cartId, result.cartId());
        assertEquals(authenticatedUserId, result.userId());
        verify(shoppingCartRepository).findById(cartId);
    }

    @Test
    public void shouldThrowExceptionWhenUnauthorizedAccessToCart() {
        UUID cartId = UUID.randomUUID();
        UUID authenticatedUserId = UUID.randomUUID();
        UUID otherUserId = UUID.randomUUID();

        User user = new User();
        user.setId(authenticatedUserId);

        User otherUser = new User();
        otherUser.setId(otherUserId);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(cartId);
        shoppingCart.setUser(otherUser);

        when(authService.getAuthenticatedUser()).thenReturn(user);
        when(shoppingCartRepository.findById(cartId)).thenReturn(Optional.of(shoppingCart));

        Exception exception = assertThrows(UnauthorizedAccessException.class, () ->
                shoppingCartService.getShoppingCartById(cartId)
        );

        assertEquals("Logged user is not authorized to view this cart.", exception.getMessage());
        verify(shoppingCartRepository).findById(cartId);
    }
}

