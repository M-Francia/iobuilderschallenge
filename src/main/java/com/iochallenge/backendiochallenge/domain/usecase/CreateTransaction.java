package com.iochallenge.backendiochallenge.domain.usecase;


import com.iochallenge.backendiochallenge.domain.model.Transaction;

public interface CreateTransaction {
    
    Transaction createTransaction(Transaction transaction);

}
