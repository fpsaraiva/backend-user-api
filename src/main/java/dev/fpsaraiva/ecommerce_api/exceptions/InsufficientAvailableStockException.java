package dev.fpsaraiva.ecommerce_api.exceptions;

public class InsufficientAvailableStockException extends RuntimeException {
    public InsufficientAvailableStockException(String message) {
        super(message);
    }
}
