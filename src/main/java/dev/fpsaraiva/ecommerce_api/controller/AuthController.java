package dev.fpsaraiva.ecommerce_api.controller;

import dev.fpsaraiva.ecommerce_api.auth.TokenService;
import dev.fpsaraiva.ecommerce_api.dto.LoginRequestDto;
import dev.fpsaraiva.ecommerce_api.dto.RegisterRequestDto;
import dev.fpsaraiva.ecommerce_api.dto.ResponseDto;
import dev.fpsaraiva.ecommerce_api.exceptions.EmailAlreadyExistsException;
import dev.fpsaraiva.ecommerce_api.model.User;
import dev.fpsaraiva.ecommerce_api.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDto loginRequestDto){
        User user = this.repository.findByEmail(loginRequestDto.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(loginRequestDto.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDto(user.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody RegisterRequestDto registerRequestDto){
        Optional<User> user = this.repository.findByEmail(registerRequestDto.email());

        if(!user.isEmpty()) {
            throw new EmailAlreadyExistsException("User already registered using email '" + registerRequestDto.email() + "'.");
        }

        User newUser = new User();
        newUser.setPassword(passwordEncoder.encode(registerRequestDto.password()));
        newUser.setEmail(registerRequestDto.email());
        newUser.setName(registerRequestDto.name());
        this.repository.save(newUser);

        String token = this.tokenService.generateToken(newUser);
        return ResponseEntity.ok(new ResponseDto(newUser.getName(), token));
    }
}
