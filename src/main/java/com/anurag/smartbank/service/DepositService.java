package com.anurag.smartbank.service;

import com.anurag.smartbank.dto.request.DepositRequest;
import com.anurag.smartbank.dto.response.DepositResponse;
import com.anurag.smartbank.entity.Account;
import com.anurag.smartbank.entity.Transaction;
import com.anurag.smartbank.entity.User;
import com.anurag.smartbank.entity.enums.AccountStatus;
import com.anurag.smartbank.entity.enums.TransactionType;
import com.anurag.smartbank.exception.AccountInactiveException;
import com.anurag.smartbank.exception.AccountNotFoundException;
import com.anurag.smartbank.repository.AccountRepository;
import com.anurag.smartbank.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
@Transactional
@Service
public class DepositService
{
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    public DepositService(AccountRepository accountRepository, TransactionRepository transactionRepository)
    {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public DepositResponse deposit(DepositRequest request)
    {
        Account account = accountRepository.findByAccountNumber(request.getAccountNumber()).orElseThrow(() -> new AccountNotFoundException("Account not found"));
        account.setBalance((account.getBalance().add(request.getDepositAmount())));
        Account savedAccount =accountRepository.save(account);
        if(savedAccount.getStatus() != AccountStatus.ACTIVE)
        {
            throw new AccountInactiveException("Account is Frozen");
        }

        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.CREDIT);
        transaction.setAmount(request.getDepositAmount());
        transaction.setDescription("Cash Deposit");
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setAccount(savedAccount);

        Transaction savedTransaction = transactionRepository.save(transaction);

        DepositResponse response = new DepositResponse();
        response.setDepositedAmount(request.getDepositAmount());
        response.setAccountNumber(account.getAccountNumber());
        response.setTransactionTime(savedTransaction.getTimestamp());
        response.setTransactionId(savedTransaction.getId());
        response.setMessage("Deposit Successfull");
        response.setCurrentBalance(savedAccount.getBalance());

        return response;

    }
}
