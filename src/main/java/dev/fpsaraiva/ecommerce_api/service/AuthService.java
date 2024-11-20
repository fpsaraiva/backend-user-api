package dev.fpsaraiva.ecommerce_api.service;

import dev.fpsaraiva.ecommerce_api.infra.auth.TokenService;
import dev.fpsaraiva.ecommerce_api.dto.AuthResponseDto;
import dev.fpsaraiva.ecommerce_api.dto.LoginRequestDto;
import dev.fpsaraiva.ecommerce_api.dto.RegisterRequestDto;
import dev.fpsaraiva.ecommerce_api.exceptions.EmailAlreadyExistsException;
import dev.fpsaraiva.ecommerce_api.model.User;
import dev.fpsaraiva.ecommerce_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenService tokenService;

    public AuthResponseDto authenticateUser(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(loginRequestDto.password(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials. Please check your password.");
        }

        String token = tokenService.generateToken(user);
        return new AuthResponseDto(user.getName(), token);
    }

    public AuthResponseDto registerUser(RegisterRequestDto registerRequestDto) {
        if (userRepository.findByEmail(registerRequestDto.email()).isPresent()) {
            throw new EmailAlreadyExistsException(
                    "User already registered using email '" + registerRequestDto.email() + "'.");
        }

        User newUser = new User();
        newUser.setPassword(passwordEncoder.encode(registerRequestDto.password()));
        newUser.setEmail(registerRequestDto.email());
        newUser.setName(registerRequestDto.name());

        userRepository.save(newUser);

        String token = tokenService.generateToken(newUser);
        return new AuthResponseDto(newUser.getName(), token);
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User is not authenticated");
        }

        Object details = authentication.getPrincipal();

        if (details instanceof User) {
            String email = ((User) details).getEmail();
            return userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
        }

        throw new RuntimeException("Unexpected principal type: ");
    }
}
