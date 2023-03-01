package com.whitebox.bank.controller;

import com.whitebox.bank.domain.Transaction;
import com.whitebox.bank.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    TransactionService transactionService;

    @PostMapping("")
    public void addTransaction(@RequestBody Transaction transaction) {

        LOG.info("Make new transaction with data: {}",transaction);
        transactionService.addTransaction(transaction);
    }

    @GetMapping("all/{accountId}")
    public List<Transaction> getAllTransactions(@PathVariable String accountId, @RequestParam String date) {

        LOG.info("Get all transactions for account id: {} and since: {}",accountId,date);
        return transactionService.getAllTransactions(accountId, date);
    }
}