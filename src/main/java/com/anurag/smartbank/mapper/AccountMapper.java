package com.anurag.smartbank.mapper;

import com.anurag.smartbank.dto.request.CreateAccountRequest;
import com.anurag.smartbank.dto.response.AccountResponse;
import com.anurag.smartbank.entity.Account;
import com.anurag.smartbank.repository.AccountRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class AccountMapper
{

    public Account toAccountEntity(CreateAccountRequest request)
    {
        Account account = new Account();
        account.setAccountType(request.getAccountType());
        return account;

    }
    public AccountResponse toAccountResponse(Account account)
    {
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setAccountNumber(account.getAccountNumber());
        accountResponse.setAccountStatus(account.getStatus());
        accountResponse.setBalance(account.getBalance());
        accountResponse.setCreatedAt(account.getCreatedAt());
        accountResponse.setAccountType(account.getAccountType());
        accountResponse.setId(account.getId());
        return accountResponse;

    }
}
