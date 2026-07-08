package com.anurag.smartbank.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class TransferRequest
{
    @NotBlank(message = "Sender Account Number cannot be blank")
    private String fromAccountNumber;
    @NotBlank(message = "Receiver Account Number cannot be blank")
    private String toAccountNumber;
    @NotNull(message = "Transaction Amount is required")
    @Positive(message = "Transaction Amount should be positive")
    private BigDecimal transactionAmount;

    public String getFromAccountNumber()
    {
        return fromAccountNumber;
    }

    public void setFromAccountNumber(String fromAccountNumber)
    {
        this.fromAccountNumber = fromAccountNumber;
    }

    public String getToAccountNumber()
    {
        return toAccountNumber;
    }

    public void setToAccountNumber(String toAccountNumber)
    {
        this.toAccountNumber = toAccountNumber;
    }

    public BigDecimal getTransactionAmount()
    {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount)
    {
        this.transactionAmount = transactionAmount;
    }
}
