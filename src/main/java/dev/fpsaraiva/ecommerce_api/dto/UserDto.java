package dev.fpsaraiva.ecommerce_api.dto;

import dev.fpsaraiva.ecommerce_api.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDto(
        @NotBlank String name,
        @Email String email,
        @NotBlank String password
) {
    public static UserDto toDto(User user) {
        return new UserDto(
                user.getName(),
                user.getEmail(),
                user.getPassword()
        );
    }
}
