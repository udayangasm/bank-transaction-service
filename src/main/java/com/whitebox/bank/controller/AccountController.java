package com.whitebox.bank.controller;

import com.whitebox.bank.domain.BankAccount;
import com.whitebox.bank.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    AccountService accountService;

    @PostMapping("")
    public BankAccount createBankAccount(@RequestBody BankAccount bankAccount) {

        LOG.info("Create new bank account with data: {} ", bankAccount);
        return accountService.createBankAccount(bankAccount);
    }

    @GetMapping("balance/{accountId}")
    public BankAccount getAccountBalance(@PathVariable String accountId) {

        LOG.info("Get account balance for account id : {} ", accountId);
        return accountService.getAccountBalance(accountId);
    }

    @GetMapping("red")
    public List<BankAccount> getAllAccountsHavingMinusBalance() {

        LOG.info("Get all red flagged accounts");
        return accountService.getAllAccountsHavingMinusBalance();
    }
}