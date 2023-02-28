package com.whitebox.bank.service.cqrs;

import com.whitebox.bank.domain.BankAccount;
import com.whitebox.bank.domain.Transaction;
import com.whitebox.bank.repository.BankAccountRepository;
import com.whitebox.bank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandServiceImpl implements CommandService {

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public BankAccount createBankAccount(BankAccount bankAccount) {

        long current = System.nanoTime();
        long random = (long) (Math.random() * 10000);
        bankAccount.setAccountId(current + "" + random);

        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public void addTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}