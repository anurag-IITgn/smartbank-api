package com.anurag.smartbank.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class DepositRequest
{
    @NotBlank(message="Account Number cannot be empty")
    private String accountNumber;
    @Positive(message="Deposit amount should be more than 0")
    @NotNull(message="Deposit amount is required")
    private BigDecimal depositAmount;
    public DepositRequest(){}

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }
}
