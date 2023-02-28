package com.whitebox.bank.service.cqrs;

import com.whitebox.bank.domain.BankAccount;
import com.whitebox.bank.domain.Transaction;
import com.whitebox.bank.domain.TransactionType;
import com.whitebox.bank.repository.BankAccountRepository;
import com.whitebox.bank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QueryServiceImpl implements QueryService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Value("${bank.overdraft-limit}")
    private int OVERDRAFT_LIMIT;

    @Override
    public BankAccount getAccountBalance(String accountId) {

        Optional<BankAccount> bankAccountOptional = bankAccountRepository.findById(accountId);

        BankAccount bankAccount = new BankAccount();
        if (bankAccountOptional.isPresent()) {

            bankAccount = bankAccountOptional.get();

            List<Transaction> transactionList = transactionRepository.findByAccountId(accountId);

            for (Transaction transaction : transactionList) {
                if (transaction.getType() == TransactionType.CREDIT) {
                    bankAccount.setBalance(bankAccount.getBalance() + transaction.getAmount());
                } else {
                    bankAccount.setBalance(bankAccount.getBalance() - transaction.getAmount());
                }
            }
        }
        return bankAccount;
    }

    @Override
    public boolean checkPendingDebitExceedOverdraftLimit(String accountId, Transaction transaction) {

        if (transaction.getType() != TransactionType.DEBIT) {
            return false;
        } else {
            BankAccount accountBalance = getAccountBalance(accountId);

            return OVERDRAFT_LIMIT + accountBalance.getBalance() < transaction.getAmount();
        }
    }

    @Override
    public List<Transaction> listAllTransaction(String accountId, LocalDateTime date) {

        return transactionRepository.findByAccountIdAndCreatedBetween(accountId, date, LocalDateTime.now());
    }

    @Override
    public List<BankAccount> getAllAccountsHavingMinusBalance() {

        List<BankAccount> minusBalanceAccountList = new ArrayList<>();
        List<BankAccount> allAccountList = bankAccountRepository.findAll();

        for (BankAccount bankAccount : allAccountList) {

            if (getAccountBalance(bankAccount.getAccountId()).getBalance() < 0) {

                minusBalanceAccountList.add(bankAccount);
            }
        }
        return minusBalanceAccountList;
    }

    @Override
    public boolean checkUserEmailAlreadyExists(String email) {

        return bankAccountRepository.existsByEmail(email);
    }

    @Override
    public boolean checkAccountExistsById(String accountId) {

        return bankAccountRepository.existsById(accountId);
    }
}