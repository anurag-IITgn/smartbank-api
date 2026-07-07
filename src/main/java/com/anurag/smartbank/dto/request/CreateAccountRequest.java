package com.anurag.smartbank.dto.request;

import com.anurag.smartbank.entity.enums.AccountType;
import jakarta.validation.constraints.NotNull;

public class CreateAccountRequest
{
    @NotNull(message = "User id is required")
    private Long userId;
    @NotNull(message = "Account type is required")
    private AccountType accountType;

    public CreateAccountRequest()
    {
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public AccountType getAccountType()
    {
        return accountType;
    }

    public void setAccountType(AccountType accountType)
    {
        this.accountType = accountType;
    }
}
