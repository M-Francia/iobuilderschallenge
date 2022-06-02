package com.iochallenge.backendiochallenge.domain.usecase;

import com.iochallenge.backendiochallenge.domain.model.Transaction;
import com.iochallenge.backendiochallenge.domain.model.Wallet;

import java.util.List;

public interface GetWalletTransactions {
    List<Transaction> getWalletTransactions (Long id);
}
