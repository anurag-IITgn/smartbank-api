package com.anurag.smartbank.service;

import com.anurag.smartbank.dto.request.CreateAccountRequest;
import com.anurag.smartbank.dto.response.AccountResponse;
import com.anurag.smartbank.entity.Account;
import com.anurag.smartbank.entity.User;
import com.anurag.smartbank.entity.enums.AccountStatus;
import com.anurag.smartbank.entity.enums.AccountType;
import com.anurag.smartbank.exception.DuplicateResourceException;
import com.anurag.smartbank.exception.UserNotFoundException;
import com.anurag.smartbank.mapper.AccountMapper;
import com.anurag.smartbank.repository.AccountRepository;
import com.anurag.smartbank.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AccountService
{
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountMapper accountMapper;

    public AccountService(AccountRepository accountRepository,
                          UserRepository userRepository,
                          AccountMapper accountMapper)
    {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.accountMapper = accountMapper;
    }

    public String generateAccNumber()
    {
        SecureRandom secureRandom = new SecureRandom();
        long min = 100_000_000_000L;
        long max = 999_999_999_999L;
        long secure12Digit = min + (long) (secureRandom.nextDouble() * (max - min + 1));
        String accNumber = String.valueOf(secure12Digit);

        while (accountRepository.existsByAccountNumber(accNumber))
        {
            secure12Digit = min + (long) (secureRandom.nextDouble() * (max - min + 1));
            accNumber = String.valueOf(secure12Digit);
        }
        return accNumber;
    }

    public AccountResponse createAccount(CreateAccountRequest request)
    {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new UserNotFoundException("User does not exist"));
        Account account = accountMapper.toAccountEntity(request);

        String accNumber=generateAccNumber();
        account.setAccountNumber(accNumber);
        account.setCreatedAt(LocalDateTime.now());
        account.setStatus(AccountStatus.ACTIVE);
        account.setBalance(BigDecimal.ZERO);
        account.setUser(user);
        Account savedAccount = accountRepository.save(account);
        return accountMapper.toAccountResponse(savedAccount);
    }
}

