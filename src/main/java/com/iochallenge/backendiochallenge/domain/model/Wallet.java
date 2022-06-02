package com.iochallenge.backendiochallenge.domain.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class Wallet {

    private Long userId;

    private String name;

    private Long id;

    private BigDecimal balance;

    private List<Transaction> transactions;

    public Wallet(String name) {
        this.name = name;
        this.balance = BigDecimal.ZERO;
        this.transactions = new ArrayList<>();
    }

    public Wallet() {
        this.transactions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public void depositAmount(BigDecimal amount) {
        this.balance = balance.add(amount);
    }

    public Boolean withdrawAmount(BigDecimal amount) {
        if(this.balance.compareTo(amount) >= 0){
            this.balance = balance.subtract(amount);
            return true;
        }
        return false;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public void addTransaction(Transaction transaction){
        this.transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    

    

    
}
