package com.iochallenge.backendiochallenge.domain.usecase;

import java.math.BigDecimal;

import com.iochallenge.backendiochallenge.domain.model.Transaction;
import com.iochallenge.backendiochallenge.domain.model.Wallet;

public class DepositUseCase {
    
    private final FindWallet findWallet;
    private final UpdateWallet updateWallet;
    private final CreateTransaction createTransaction;

    

    public DepositUseCase(FindWallet findWallet, UpdateWallet updateWallet, CreateTransaction createTransaction) {
        this.findWallet = findWallet;
        this.updateWallet = updateWallet;
        this.createTransaction = createTransaction;
    }



    public Boolean deposit(BigDecimal amount, Long id){
        Wallet wallet = findWallet.findWallet(id);
        if(wallet != null){
            Transaction transaction = new Transaction("Deposit", amount);
            transaction.setWalletId(id);
            wallet.addTransaction(createTransaction.createTransaction(transaction));
            wallet.depositAmount(amount);
            updateWallet.updateWallet(wallet);
            return true;
        }
        return false;
    }
    


}
