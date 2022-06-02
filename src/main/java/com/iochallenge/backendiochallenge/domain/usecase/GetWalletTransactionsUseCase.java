package com.iochallenge.backendiochallenge.domain.usecase;

import com.iochallenge.backendiochallenge.domain.model.Transaction;

import java.util.List;

public class GetWalletTransactionsUseCase {

    private final GetWalletTransactions getWalletTransactions;

    public GetWalletTransactionsUseCase(GetWalletTransactions getWalletTransactions) {
        this.getWalletTransactions = getWalletTransactions;
    }

    public List<Transaction> getWalletTransactionsUseCase(Long id){
        //Refactor
        return getWalletTransactions.getWalletTransactions(id);
    }
}
