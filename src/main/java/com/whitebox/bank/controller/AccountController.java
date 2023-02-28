package com.whitebox.bank.controller;

import com.whitebox.bank.domain.BankAccount;
import com.whitebox.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping("")
    public BankAccount createBankAccount(@RequestBody BankAccount bankAccount) {

        return accountService.createBankAccount(bankAccount);
    }

    @GetMapping("balance/{accountId}")
    public BankAccount getAccountBalance(@PathVariable String accountId) {

        return accountService.getAccountBalance(accountId);
    }

    @GetMapping("red")
    public List<BankAccount> getAllAccountsHavingMinusBalance() {

        return accountService.getAllAccountsHavingMinusBalance();
    }
}