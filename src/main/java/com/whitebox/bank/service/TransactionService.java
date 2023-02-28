package com.whitebox.bank.service;

import com.whitebox.bank.domain.Transaction;
import com.whitebox.bank.domain.TransactionType;
import com.whitebox.bank.exception.AccountNotFoundException;
import com.whitebox.bank.exception.InvalidAmountException;
import com.whitebox.bank.exception.MandatoryFieldNotFoundException;
import com.whitebox.bank.service.cqrs.CommandServiceImpl;
import com.whitebox.bank.service.cqrs.QueryServiceImpl;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    CommandServiceImpl commandService;

    @Autowired
    QueryServiceImpl queryService;

    public void addTransaction(Transaction transaction) {

        validateInput(transaction);
        transaction.setCreated(LocalDateTime.now());
        commandService.addTransaction(transaction);
    }

    private void validateInput(Transaction transaction) {

        if (StringUtils.isEmpty(transaction.getAccountId())) {
            throw new MandatoryFieldNotFoundException("Account Id can not be empty");
        }

        if (transaction.getAmount() <= 0) {
            throw new InvalidAmountException("Amount should be grater than zero");
        }

        if (transaction.getType() == null || (transaction.getType() != TransactionType.CREDIT && transaction.getType() != TransactionType.DEBIT)) {
            throw new MandatoryFieldNotFoundException("Transaction type not found");
        }

        if (!queryService.checkAccountExistsById(transaction.getAccountId())) {
            throw new AccountNotFoundException("Account not found for the Id : " + transaction.getAccountId());
        }
    }

    public List<Transaction> getAllTransactions(String accountId, String dateStr) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime date = LocalDateTime.parse(dateStr, formatter);
        return queryService.listAllTransaction(accountId, date);
    }
}