package com.iochallenge.backendiochallenge.domain.usecase;

import java.math.BigDecimal;

import com.iochallenge.backendiochallenge.domain.exception.TransferException;
import com.iochallenge.backendiochallenge.domain.model.Transaction;
import com.iochallenge.backendiochallenge.domain.model.Wallet;

public class TransferUseCase {
    
    private final FindWallet findWallet;
    private final UpdateWallet updateWallet;
    private final CreateTransaction createTransaction;


    public TransferUseCase(FindWallet findWallet, UpdateWallet updateWallet, CreateTransaction createTransaction) {
        this.findWallet = findWallet;
        this.updateWallet = updateWallet;
        this.createTransaction = createTransaction;
    }


    public Boolean transfer(BigDecimal amount, long fromId, Long toId, Long userId){
        Wallet fromWallet = findWallet.findWallet(fromId);

        if (fromWallet.getUserId() == userId){

            Wallet toWallet = findWallet.findWallet(toId);
            if(toWallet != null){

                if(fromWallet.withdrawAmount(amount) == true){

                    toWallet.depositAmount(amount);

                    Transaction fromTransaction = new Transaction("Transfer to: " + toId , amount.negate());
                    fromTransaction.setWalletId(fromId);

                    Transaction toTransaction = new Transaction("Transfer from: "+ fromId , amount);
                    toTransaction.setWalletId(toId);

                    fromWallet.addTransaction(createTransaction.createTransaction(fromTransaction));
                    toWallet.addTransaction(createTransaction.createTransaction(toTransaction));

                    updateWallet.updateWallet(fromWallet);
                    updateWallet.updateWallet(toWallet);
                    return true;
                }
                throw new TransferException(fromWallet.getId() + " wallet donesn´t have enought amount");
            }
        }

        return false;
    }

    

}
