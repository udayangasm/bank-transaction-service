package com.whitebox.bank.repository;

import com.whitebox.bank.domain.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {

    List<Transaction> findByAccountId(String accountId);

    List<Transaction> findByAccountIdAndCreatedBetween(String accountId, LocalDateTime date, LocalDateTime now);
}
