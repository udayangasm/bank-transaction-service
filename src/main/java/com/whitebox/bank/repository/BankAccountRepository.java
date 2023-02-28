package com.whitebox.bank.repository;

import com.whitebox.bank.domain.BankAccount;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends MongoRepository<BankAccount, String> {

    boolean existsByEmail(String email);
}