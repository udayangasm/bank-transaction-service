package com.whitebox.bank.service;

import com.whitebox.bank.domain.Transaction;
import com.whitebox.bank.domain.TransactionType;
import com.whitebox.bank.exception.AccountNotFoundException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @InjectMocks
    TransactionService transactionService;

    @Mock
    CommandServiceImpl commandService;

    @Mock
    QueryServiceImpl queryService;

    @Test
    public void Test_add_transaction() {
        //Arrange
        Transaction transaction = new Transaction();
        transaction.setAccountId("6199315377343002162");
        transaction.setAmount(100);
        transaction.setType(TransactionType.CREDIT);
        when(queryService.checkAccountExistsById(any())).thenReturn(true);
        //Act
        //Assert
        assertDoesNotThrow(() -> transactionService.addTransaction(transaction));
    }

    @Test
    public void Test_add_transaction_when_account_id_is_empty() {
        //Arrange
        Transaction transaction = new Transaction();
        transaction.setAccountId("");
        transaction.setAmount(100);
        transaction.setType(TransactionType.CREDIT);
        //Act
        //Assert
        assertThrows(MandatoryFieldNotFoundException.class, () -> transactionService.addTransaction(transaction));
    }

    @Test
    public void Test_add_transaction_when_account_is_zero() {
        //Arrange
        Transaction transaction = new Transaction();
        transaction.setAccountId("6199315377343002162");
        transaction.setAmount(0);
        transaction.setType(TransactionType.CREDIT);
        //Act
        //Assert
        assertThrows(InvalidAmountException.class, () -> transactionService.addTransaction(transaction));
    }

    @Test
    public void Test_add_transaction_when_transaction_type_is_null() {
        //Arrange
        Transaction transaction = new Transaction();
        transaction.setAccountId("6199315377343002162");
        transaction.setAmount(100);
        //Act
        //Assert
        assertThrows(MandatoryFieldNotFoundException.class, () -> transactionService.addTransaction(transaction));
    }

    @Test
    public void Test_add_transaction_when_account_not_found() {
        //Arrange
        Transaction transaction = new Transaction();
        transaction.setAccountId("6199315377343002162");
        transaction.setAmount(100);
        transaction.setType(TransactionType.CREDIT);
        when(queryService.checkAccountExistsById(any())).thenReturn(false);
        //Act
        //Assert
        assertThrows(AccountNotFoundException.class, () -> transactionService.addTransaction(transaction));
    }
}