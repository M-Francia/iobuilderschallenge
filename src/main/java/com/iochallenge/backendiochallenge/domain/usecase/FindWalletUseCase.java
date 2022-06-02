package com.iochallenge.backendiochallenge.domain.usecase;

import com.iochallenge.backendiochallenge.domain.model.Wallet;

public class FindWalletUseCase {

    private final FindWallet findWallet;

    public FindWalletUseCase(FindWallet findWallet) {
        this.findWallet = findWallet;
    }


    public Wallet findWallet(Long id){
        return findWallet.findWallet(id);
    }
    



}
