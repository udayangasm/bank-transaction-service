package com.whitebox.bank.service;

import com.whitebox.bank.domain.BankAccount;
import com.whitebox.bank.exception.InvalidAmountException;
import com.whitebox.bank.exception.MandatoryFieldNotFoundException;
import com.whitebox.bank.exception.UserAlreadyExistsException;
import com.whitebox.bank.service.cqrs.CommandServiceImpl;
import com.whitebox.bank.service.cqrs.QueryServiceImpl;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    CommandServiceImpl commandService;

    @Autowired
    QueryServiceImpl queryService;

    public BankAccount createBankAccount(BankAccount bankAccount) {

        validateInputs(bankAccount);
        return commandService.createBankAccount(bankAccount);
    }

    private void validateInputs(BankAccount bankAccount) {

        if (StringUtils.isEmpty(bankAccount.getEmail())) {
            throw new MandatoryFieldNotFoundException("Email is not found");
        }

        if (StringUtils.isEmpty(bankAccount.getName())) {
            throw new MandatoryFieldNotFoundException("Name is not found");
        }

        if (bankAccount.getBalance() <= 0) {
            throw new InvalidAmountException("Initial deposit should be grater than zero");
        }

        if (queryService.checkUserEmailAlreadyExists(bankAccount.getEmail())) {
            throw new UserAlreadyExistsException("user email address " + bankAccount.getEmail() + ", is already exists");
        }
    }

    public BankAccount getAccountBalance(String accountId) {

        return queryService.getAccountBalance(accountId);
    }

    public List<BankAccount> getAllAccountsHavingMinusBalance() {

        return queryService.getAllAccountsHavingMinusBalance();
    }
}