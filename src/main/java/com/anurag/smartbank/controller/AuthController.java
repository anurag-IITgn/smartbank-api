package com.anurag.smartbank.controller;

import com.anurag.smartbank.dto.request.LoginRequest;
import com.anurag.smartbank.dto.response.LoginResponse;
import com.anurag.smartbank.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "User authentication and JWT generation")
@RestController
@RequestMapping("/auth")
public class AuthController
{
    private final AuthService authService;
    public AuthController(AuthService authService)
    {
        this.authService = authService;
    }

    @Operation(summary = "Login user", description = "Authenticates the user and returns a JWT token.")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request)
    {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
