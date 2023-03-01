package com.whitebox.bank.domain;

import java.time.LocalDateTime;

public class Transaction {

    private String accountId;
    private LocalDateTime created;
    private TransactionType type;
    private double amount;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String toString(){
        return "[ accountId: "+accountId+" ,type: "+type+" , amount: "+amount+" ,created: "+created+" ]";
    }
}