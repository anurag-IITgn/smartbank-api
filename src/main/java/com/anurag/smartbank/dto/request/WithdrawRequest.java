package com.anurag.smartbank.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class WithdrawRequest
{
    @NotBlank(message="Account Number cannot be empty")
    private String accountNumber;
    @Positive(message="Withdraw amount should be more than 0")
    @NotNull(message="Withdraw amount is required")
    private BigDecimal withdrawAmount;
    public WithdrawRequest(){}

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }


    public void setWithdrawAmount(BigDecimal withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public BigDecimal getWithdrawAmount() {
        return withdrawAmount;
    }
}
