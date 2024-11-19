package dev.fpsaraiva.ecommerce_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequestDto (
        @NotBlank(message = "Name must be informed") String name,
        @NotBlank(message = "Email must be informed") @Email(message = "Email format must be valid") String email,
        @NotBlank(message = "Password must be informed") String password) {}
