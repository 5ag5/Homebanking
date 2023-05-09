package com.mindhub.homebanking.Repositories;

import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RepositoryRestResource
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Set<Transaction> findAccountsByAccountAndDateBetween(Account account, LocalDateTime startDate, LocalDateTime endDate);
}
