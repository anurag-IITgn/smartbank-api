package com.anurag.smartbank.controller;

import com.anurag.smartbank.dto.request.LoginRequest;
import com.anurag.smartbank.dto.response.LoginResponse;
import com.anurag.smartbank.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController
{
    private final AuthService authService;
    public AuthController(AuthService authService)
    {
        this.authService = authService;
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request)
    {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
