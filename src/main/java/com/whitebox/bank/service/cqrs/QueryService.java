package com.whitebox.bank.service.cqrs;

import com.whitebox.bank.domain.BankAccount;
import com.whitebox.bank.domain.Transaction;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public interface QueryService {

    BankAccount getAccountBalance(String accountId);

    boolean checkPendingDebitExceedOverdraftLimit(String accountId, Transaction transaction);

    List<Transaction> listAllTransaction(String accountId, LocalDateTime date);

    List<BankAccount> getAllAccountsHavingMinusBalance();

    boolean checkUserEmailAlreadyExists(String email);

    boolean checkAccountExistsById(String accountId);
}
