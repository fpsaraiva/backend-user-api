package dev.fpsaraiva.ecommerce_api.exceptions;

public class DuplicateSkuException extends RuntimeException {
    public DuplicateSkuException(String message) {
        super(message);
    }
}
