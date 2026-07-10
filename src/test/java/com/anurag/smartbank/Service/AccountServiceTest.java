package com.anurag.smartbank.Service;

import com.anurag.smartbank.dto.request.CreateAccountRequest;
import com.anurag.smartbank.dto.response.AccountResponse;
import com.anurag.smartbank.entity.Account;
import com.anurag.smartbank.entity.User;
import com.anurag.smartbank.entity.enums.AccountStatus;
import com.anurag.smartbank.entity.enums.AccountType;
import com.anurag.smartbank.exception.UserNotFoundException;
import com.anurag.smartbank.mapper.AccountMapper;
import com.anurag.smartbank.repository.AccountRepository;
import com.anurag.smartbank.repository.UserRepository;
import com.anurag.smartbank.service.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static com.anurag.smartbank.entity.enums.AccountType.SAVINGS;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest
{
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AccountMapper accountMapper;
    @InjectMocks
    private AccountService accountService;

    @Test
    void testCreateAccount()
    {
        CreateAccountRequest request = new CreateAccountRequest();
        request.setAccountType(SAVINGS);
        request.setUserId(1L);

        User user = new User();
        user.setId(1L);
        user.setName("Anurag");

        Account account = new Account();
        account.setAccountType(SAVINGS);

        AccountResponse response = new AccountResponse();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(accountMapper.toAccountEntity(request)).thenReturn(account);
        when(accountRepository.existsByAccountNumber(anyString())).thenReturn(false);
        when(accountRepository.save(account)).thenReturn(account);
        when(accountMapper.toAccountResponse(account)).thenReturn(response);

        AccountResponse result = accountService.createAccount(request);

        response.setId(1L);
        response.setAccountNumber("123456789012");
        response.setAccountType(SAVINGS);

        Assertions.assertEquals(response.getId(), result.getId());
        Assertions.assertEquals(response.getAccountType(), result.getAccountType());

        verify(userRepository).findById(1L);
        verify(accountRepository).save(account);
        verify(accountMapper).toAccountResponse(account);

    }
    @Test
    void testUserNotFound()
    {
        CreateAccountRequest request = new CreateAccountRequest();

        request.setUserId(1L);
        request.setAccountType(AccountType.SAVINGS);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> accountService.createAccount(request));

        verify(userRepository).findById(1L);
        verify(accountRepository, never()).save(any(Account.class));
    }
    @Test
    void testAccountDefaultsBeforeSaving()
    {
        CreateAccountRequest request = new CreateAccountRequest();
        request.setAccountType(SAVINGS);
        request.setUserId(1L);

        User user = new User();
        user.setId(1L);
        user.setName("Anurag");

        Account account = new Account();
        account.setAccountType(SAVINGS);

        AccountResponse response = new AccountResponse();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(accountMapper.toAccountEntity(request)).thenReturn(account);
        when(accountRepository.existsByAccountNumber(anyString())).thenReturn(false);
        when(accountRepository.save(account)).thenReturn(account);
        when(accountMapper.toAccountResponse(account)).thenReturn(response);

        accountService.createAccount(request);

        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository).save(accountCaptor.capture());
        Account savedAccount = accountCaptor.getValue();

        Assertions.assertEquals(BigDecimal.ZERO, savedAccount.getBalance());
        Assertions.assertEquals(AccountStatus.ACTIVE, savedAccount.getStatus());
        Assertions.assertEquals(user, savedAccount.getUser());
        Assertions.assertNotNull(savedAccount.getCreatedAt());
        Assertions.assertNotNull(savedAccount.getAccountNumber());

    }
    @Test
    void testGenerateAccountNumberIsUnique()
    {

        when(accountRepository.existsByAccountNumber(anyString())).thenReturn(true, false);
        String accountNumber = accountService.generateAccNumber();

        Assertions.assertNotNull(accountNumber);
        Assertions.assertEquals(12, accountNumber.length());
        verify(accountRepository, times(2)).existsByAccountNumber(anyString());
    }
}
