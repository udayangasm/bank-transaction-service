package com.whitebox.bank.service.cqrs;

import com.whitebox.bank.domain.BankAccount;
import com.whitebox.bank.domain.Transaction;
import com.whitebox.bank.domain.TransactionType;
import com.whitebox.bank.repository.BankAccountRepository;
import com.whitebox.bank.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QueryServiceImplTest {

    @InjectMocks
    QueryServiceImpl queryService;

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    BankAccountRepository bankAccountRepository;

    @Test
    public void Test_get_account_balance() {
        //Arrange
        when(bankAccountRepository.findById("6199315377343002162")).thenReturn(getBankAccount());
        when(transactionRepository.findByAccountId("6199315377343002162")).thenReturn(getTransactionList());
        //Act
        BankAccount bankAccount = queryService.getAccountBalance("6199315377343002162");
        //Assert
        assertEquals(550, bankAccount.getBalance());
    }

    @Test
    public void Test_check_pending_debit_exceed_overdraft_limit() {
        //Arrange
        Transaction transaction = new Transaction();
        transaction.setAmount(700);
        transaction.setAccountId("6199315377343002162");
        transaction.setType(TransactionType.DEBIT);
        when(bankAccountRepository.findById("6199315377343002162")).thenReturn(getBankAccount());
        when(transactionRepository.findByAccountId("6199315377343002162")).thenReturn(getTransactionList());
        ReflectionTestUtils.setField(queryService, "OVERDRAFT_LIMIT", 100);
        //Act
        //Assert
        assertTrue(queryService.checkPendingDebitExceedOverdraftLimit("6199315377343002162", transaction));
    }

    @Test
    public void Test_get_all_accounts_having_minus_balance() {
        //Arrange
        when(bankAccountRepository.findAll()).thenReturn(getAllAccountList());
        when(bankAccountRepository.findById("6199315377343002162")).thenReturn(getBankAccount());
        when(transactionRepository.findByAccountId("6199315377343002162")).thenReturn(getTransactionList());
        when(bankAccountRepository.findById("6199315377343002163")).thenReturn(getBankAccount2());
        when(transactionRepository.findByAccountId("6199315377343002163")).thenReturn(getTransactionList2());
        //Act
        List<BankAccount> redFlagBankAccountList = queryService.getAllAccountsHavingMinusBalance();
        //Assert
        assertEquals(1, redFlagBankAccountList.size());
        assertEquals("6199315377343002163", redFlagBankAccountList.get(0).getAccountId());
        assertEquals("user2@gmail.com", redFlagBankAccountList.get(0).getEmail());
    }

    private Optional<BankAccount> getBankAccount() {

        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(100);
        bankAccount.setAccountId("6199315377343002162");
        bankAccount.setName("user");
        bankAccount.setEmail("user@gmail.com");
        return Optional.of(bankAccount);
    }

    private List<Transaction> getTransactionList() {

        List<Transaction> transactionList = new ArrayList<>();

        Transaction transaction1 = new Transaction();
        transaction1.setAmount(100);
        transaction1.setAccountId("6199315377343002162");
        transaction1.setType(TransactionType.CREDIT);
        transactionList.add(transaction1);

        Transaction transaction2 = new Transaction();
        transaction2.setAmount(400);
        transaction2.setAccountId("6199315377343002162");
        transaction2.setType(TransactionType.CREDIT);
        transactionList.add(transaction2);

        Transaction transaction3 = new Transaction();
        transaction3.setAmount(50);
        transaction3.setAccountId("6199315377343002162");
        transaction3.setType(TransactionType.DEBIT);
        transactionList.add(transaction3);

        return transactionList;
    }

    private List<BankAccount> getAllAccountList() {

        List<BankAccount> accountList = new ArrayList<>();

        BankAccount bankAccount1 = new BankAccount();
        bankAccount1.setBalance(100);
        bankAccount1.setAccountId("6199315377343002162");
        bankAccount1.setName("user");
        bankAccount1.setEmail("user@gmail.com");
        accountList.add(bankAccount1);

        BankAccount bankAccount2 = new BankAccount();
        bankAccount2.setBalance(200);
        bankAccount2.setAccountId("6199315377343002163");
        bankAccount2.setName("user2");
        bankAccount2.setEmail("user2@gmail.com");
        accountList.add(bankAccount2);

        return accountList;
    }

    private Optional<BankAccount> getBankAccount2() {

        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(200);
        bankAccount.setAccountId("6199315377343002163");
        bankAccount.setName("user2");
        bankAccount.setEmail("user2@gmail.com");
        return Optional.of(bankAccount);
    }

    private List<Transaction> getTransactionList2() {

        List<Transaction> transactionList = new ArrayList<>();

        Transaction transaction1 = new Transaction();
        transaction1.setAmount(100);
        transaction1.setAccountId("6199315377343002163");
        transaction1.setType(TransactionType.DEBIT);
        transactionList.add(transaction1);

        Transaction transaction2 = new Transaction();
        transaction2.setAmount(400);
        transaction2.setAccountId("6199315377343002163");
        transaction2.setType(TransactionType.DEBIT);
        transactionList.add(transaction2);

        return transactionList;
    }
}