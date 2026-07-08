package com.anurag.smartbank.service;

import com.anurag.smartbank.dto.request.TransferRequest;
import com.anurag.smartbank.dto.response.DepositResponse;
import com.anurag.smartbank.dto.response.TransferResponse;
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
public class TransferService
{
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    public TransferService(AccountRepository accountRepository, TransactionRepository transactionRepository)
    {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public TransferResponse transfer(TransferRequest request)
    {


        Account senderAccount = accountRepository.findByAccountNumber(request.getFromAccountNumber()).orElseThrow(() -> new AccountNotFoundException("Sender Account not present"));
        Account receiverAccount = accountRepository.findByAccountNumber(request.getToAccountNumber()).orElseThrow(() -> new AccountNotFoundException("Receiver Account not present"));

        if(senderAccount.getStatus() != AccountStatus.ACTIVE)
        {
            throw new AccountInactiveException(" Your Account is Frozen");
        }
        if(receiverAccount.getStatus() != AccountStatus.ACTIVE)
        {
            throw new AccountInactiveException("Receiver Account is Frozen");
        }

        if(senderAccount.getBalance().compareTo(request.getTransactionAmount()) < 0)
        {
            throw new InsufficientBalanceException("Insufficient Balance. Available Balance is: " + senderAccount.getBalance());
        }

        senderAccount.setBalance(senderAccount.getBalance().subtract(request.getTransactionAmount()));
        receiverAccount.setBalance(receiverAccount.getBalance().add(request.getTransactionAmount()));

        Account savedSenderAccount = accountRepository.save(senderAccount);
        Account savedReceiverAccount = accountRepository.save(receiverAccount);


        Transaction transactionDebit = new Transaction();
        Transaction transactionCredit = new Transaction();

        transactionDebit.setType(TransactionType.DEBIT);
        transactionDebit.setAmount(request.getTransactionAmount());
        transactionDebit.setDescription("Transfer to " + receiverAccount.getAccountNumber());
        transactionDebit.setTimestamp(LocalDateTime.now());
        transactionDebit.setAccount(savedSenderAccount);

        transactionCredit.setType(TransactionType.CREDIT);
        transactionCredit.setAmount(request.getTransactionAmount());
        transactionCredit.setDescription("Transfer from " + senderAccount.getAccountNumber());
        transactionCredit.setTimestamp(LocalDateTime.now());
        transactionCredit.setAccount(savedReceiverAccount);

        Transaction savedSentTransaction = transactionRepository.save(transactionDebit);
        Transaction savedReceivedTransaction = transactionRepository.save(transactionCredit);

        TransferResponse response = new TransferResponse();
        response.setTransferredAmount(request.getTransactionAmount());
        response.setFromAccountNumber(senderAccount.getAccountNumber());
        response.setToAccountNumber(receiverAccount.getAccountNumber());
        response.setTransactionTime(transactionDebit.getTimestamp());
        response.setTransactionId(savedSentTransaction.getId());
        response.setMessage("Amount Sent Successfully");
        response.setSenderBalance(savedSenderAccount.getBalance());

        return response;


    }

}
