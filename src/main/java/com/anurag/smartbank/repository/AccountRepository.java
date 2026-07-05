package com.anurag.smartbank.repository;

import com.anurag.smartbank.entity.Account;
import com.anurag.smartbank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long>
{
    List<Account> findByUser(User user);
    List<Account> findByUserId(Long id);
    boolean existsByAccountNumber(String accountNumber);
}
