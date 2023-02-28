package com.whitebox.bank.controller;

import com.whitebox.bank.domain.Transaction;
import com.whitebox.bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("")
    public void addTransaction(@RequestBody Transaction transaction) {

        transactionService.addTransaction(transaction);
    }

    @GetMapping("all/{accountId}")
    public List<Transaction> getAllTransactions(@PathVariable String accountId, @RequestParam String date) {

        return transactionService.getAllTransactions(accountId, date);
    }
}