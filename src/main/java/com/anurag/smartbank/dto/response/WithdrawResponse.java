package com.anurag.smartbank.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class WithdrawResponse
{
    private Long transactionId;
    private String accountNumber;
    private BigDecimal withdrawAmount;
    private BigDecimal currentBalance;
    private LocalDateTime transactionTime;
    private String message;

    public Long getTransactionId()
    {
        return transactionId;
    }

    public void setTransactionId(Long transactionId)
    {
        this.transactionId = transactionId;
    }

    public String getAccountNumber()
    {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getWithdrawAmount()
    {
        return withdrawAmount;
    }

    public void setWithdrawAmount(BigDecimal withdrawAmount)
    {
        this.withdrawAmount = withdrawAmount;
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

}
