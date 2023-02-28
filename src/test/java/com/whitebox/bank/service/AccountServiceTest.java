package com.whitebox.bank.service;

import com.whitebox.bank.domain.BankAccount;
import com.whitebox.bank.exception.InvalidAmountException;
import com.whitebox.bank.exception.MandatoryFieldNotFoundException;
import com.whitebox.bank.service.cqrs.CommandServiceImpl;
import com.whitebox.bank.service.cqrs.QueryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @InjectMocks
    AccountService accountService;

    @Mock
    CommandServiceImpl commandService;

    @Mock
    QueryServiceImpl queryService;

    @Test
    public void Test_create_bank_account() {
        //Arrange
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountId("6199315377343002162");
        bankAccount.setBalance(100);
        bankAccount.setEmail("user@gmail.com");
        bankAccount.setName("user1");
        //Act
        //Assert
        assertDoesNotThrow(() -> accountService.createBankAccount(bankAccount));
    }

    @Test
    public void Test_create_bank_account_throw_mandatory_field_not_found_exception_when_email_is_empty() {
        //Arrange
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(100);
        bankAccount.setEmail("");
        bankAccount.setName("user1");
        //Act
        //Assert
        assertThrows(MandatoryFieldNotFoundException.class, () -> accountService.createBankAccount(bankAccount));
    }

    @Test
    public void Test_create_bank_account_throw_mandatory_field_not_found_exception_when_user_is_null() {
        //Arrange
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(100);
        bankAccount.setEmail("user@gmail.com");
        //Act
        //Assert
        assertThrows(MandatoryFieldNotFoundException.class, () -> accountService.createBankAccount(bankAccount));
    }

    @Test
    public void Test_create_bank_account_throw_mandatory_field_not_found_exception_when_balance_is_zero() {
        //Arrange
        BankAccount bankAccount = new BankAccount();
        bankAccount.setName("user1");
        bankAccount.setBalance(0);
        bankAccount.setEmail("user@gmail.com");
        //Act
        //Assert
        assertThrows(InvalidAmountException.class, () -> accountService.createBankAccount(bankAccount));
    }
}