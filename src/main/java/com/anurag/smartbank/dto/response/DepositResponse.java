package com.anurag.smartbank.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DepositResponse
{
    private Long transactionId;
    private String accountNumber;
    private BigDecimal depositedAmount;
    private BigDecimal currentBalance;
    private LocalDateTime transactionTime;
    private String message;

    public Long getTransactionId()
    {
        return transactionId;
    }

    public void setTransactionId(Long transactionReference)
    {
        this.transactionId = transactionReference;
    }

    public String getAccountNumber()
    {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getDepositedAmount()
    {
        return depositedAmount;
    }

    public void setDepositedAmount(BigDecimal depositedAmount)
    {
        this.depositedAmount = depositedAmount;
    }

    public BigDecimal getCurrentBalance()
    {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance)
    {
        this.currentBalance = currentBalance;
    }

    public LocalDateTime getTransactionTime()
    {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime)
    {
        this.transactionTime = transactionTime;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public void setTransactionI() {
    }
}
