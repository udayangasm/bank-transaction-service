package com.whitebox.bank.service.cqrs;

import com.whitebox.bank.domain.BankAccount;
import com.whitebox.bank.domain.Transaction;
import org.springframework.stereotype.Component;

@Component
public interface CommandService {

    BankAccount createBankAccount(BankAccount bankAccount);

    void addTransaction(Transaction transaction);
}