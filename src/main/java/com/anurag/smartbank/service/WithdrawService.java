package com.anurag.smartbank.service;


import com.anurag.smartbank.dto.request.WithdrawRequest;
import com.anurag.smartbank.dto.response.WithdrawResponse;
import com.anurag.smartbank.entity.Account;
import com.anurag.smartbank.entity.Transaction;
import com.anurag.smartbank.entity.enums.AccountStatus;
import com.anurag.smartbank.entity.enums.TransactionType;
import com.anurag.smartbank.exception.AccountInactiveException;
import com.anurag.smartbank.exception.AccountNotFoundException;
import com.anurag.smartbank.exception.InsufficientBalanceException;
import com.anurag.smartbank.repository.AccountRepository;
import com.anurag.smartbank.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
@Transactional
public class WithdrawService
{
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    public WithdrawService(AccountRepository accountRepository, TransactionRepository transactionRepository)
    {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }
    public WithdrawResponse withdraw(WithdrawRequest request)
    {
        Account account = accountRepository.findByAccountNumber(request.getAccountNumber()).orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if(account.getBalance().compareTo(request.getWithdrawAmount()) < 0)
        {
            throw new InsufficientBalanceException("Insufficient Balance. Available Balance is: " + account.getBalance());
        }

        Account savedAccount = accountRepository.save(account);

        if(savedAccount.getStatus() != AccountStatus.ACTIVE)
        {
            throw new AccountInactiveException("Account is Frozen");
        }

        account.setBalance(account.getBalance().subtract(request.getWithdrawAmount()));



        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.DEBIT);
        transaction.setAmount(request.getWithdrawAmount());
        transaction.setDescription("Money Successfully Withdrawn");
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setAccount(savedAccount);
        Transaction savedTransaction = transactionRepository.save(transaction);

        WithdrawResponse response = new WithdrawResponse();

        response.setAccountNumber(account.getAccountNumber());
        response.setWithdrawAmount(savedTransaction.getAmount());
        response.setMessage("Withdrawal Successful");
        response.setTransactionTime(savedTransaction.getTimestamp());
        response.setTransactionId(savedTransaction.getId());
        response.setCurrentBalance(savedAccount.getBalance());

        return response;
    }
}
