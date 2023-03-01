package com.whitebox.bank.domain;

import org.springframework.data.annotation.Id;

public class BankAccount {

    @Id
    private String accountId;
    private String email;
    private String name;
    private double balance;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String toString(){
        return "[ accountId: "+accountId+" ,email: "+email+" , name: "+name+" ,balance: "+balance+" ]";
    }
}