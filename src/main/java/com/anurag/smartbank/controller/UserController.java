package com.anurag.smartbank.controller;

import com.anurag.smartbank.dto.request.CreateUserRequest;
import com.anurag.smartbank.dto.response.UserResponse;
import com.anurag.smartbank.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User Management", description = "APIs for user registration and management")
@RestController
@RequestMapping("/users")
public class UserController
{
    private final UserService userService;
    public UserController(UserService userService)
    {
        this.userService=userService;
    }
    @Operation(summary = "Register a new user", description = "Creates a new SmartBank user after validating email and phone uniqueness.")
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request)
    {
        UserResponse userResponse = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

}
