package dev.fpsaraiva.ecommerce_api.controller;

import dev.fpsaraiva.ecommerce_api.dto.AuthResponseDto;
import dev.fpsaraiva.ecommerce_api.dto.LoginRequestDto;
import dev.fpsaraiva.ecommerce_api.dto.RegisterRequestDto;
import dev.fpsaraiva.ecommerce_api.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto){
        AuthResponseDto response = authService.authenticateUser(loginRequestDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        AuthResponseDto response = authService.registerUser(registerRequestDto);
        return ResponseEntity.ok(response);
    }
}
