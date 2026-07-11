package com.anurag.smartbank.controller;

import com.anurag.smartbank.dto.request.CreateAccountRequest;
import com.anurag.smartbank.dto.response.AccountResponse;
import com.anurag.smartbank.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/accounts")
@Tag(name = "Account Management", description = "APIs for bank account creation and management")
@RestController
public class AccountController
{
    private final AccountService accountService;

    public AccountController(AccountService accountService)
    {
        this.accountService = accountService;
    }

    @Operation(summary = "Create a bank account",
            description = "Creates a new account for an existing SmartBank user.")
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        AccountResponse accountResponse = accountService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountResponse);
    }
}
