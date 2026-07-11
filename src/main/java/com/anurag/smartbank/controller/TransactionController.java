package com.anurag.smartbank.controller;

import com.anurag.smartbank.dto.request.DepositRequest;
import com.anurag.smartbank.dto.request.TransferRequest;
import com.anurag.smartbank.dto.request.WithdrawRequest;
import com.anurag.smartbank.dto.response.DepositResponse;
import com.anurag.smartbank.dto.response.TransferResponse;
import com.anurag.smartbank.dto.response.WithdrawResponse;
import com.anurag.smartbank.service.DepositService;
import com.anurag.smartbank.service.TransferService;
import com.anurag.smartbank.service.WithdrawService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Transaction Management", description = "APIs for deposits, withdrawals and fund transfers")
@RequestMapping("/transactions")
public class TransactionController
{
    private final DepositService depositService;
    private final WithdrawService withdrawService;
    private final TransferService transferService;

    public TransactionController(DepositService depositService, WithdrawService withdrawService, TransferService transferService)
    {
        this.depositService = depositService;
        this.withdrawService = withdrawService;
        this.transferService = transferService;
    }
    @Operation(summary = "Deposit money", description = "Deposits money into an active account.")
    @PostMapping("/deposit")
    public ResponseEntity<DepositResponse> performDeposit(@Valid @RequestBody DepositRequest request)
    {
        DepositResponse response = depositService.deposit(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
    @Operation(summary = "Withdraw money", description = "Withdraws money from an active account after checking sufficient balance.")
    @PostMapping("/withdraw")
    public ResponseEntity<WithdrawResponse> performWithdrawal(@Valid @RequestBody WithdrawRequest request)
    {
        WithdrawResponse response = withdrawService.withdraw(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
    @Operation(summary = "Transfer money", description = "Transfers money between two active accounts.")
    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> performTransfer(@Valid @RequestBody TransferRequest request)
    {
        TransferResponse response = transferService.transfer(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
