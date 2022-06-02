package com.iochallenge.backendiochallenge.domain.usecase;

import com.iochallenge.backendiochallenge.domain.model.Wallet;

public class CreateWalletUseCase {
    

    private final CreateWallet createWallet;
    private final  FindUserById findUserById;

    public CreateWalletUseCase(CreateWallet createWallet, FindUserById findUserById) {
        this.createWallet = createWallet;
        this.findUserById = findUserById;
    }


    public Wallet createWallet(Long user_id, String walletName){
        //Refactor
        Wallet wallet = new Wallet(walletName);
        wallet.setUserId(user_id);
        return createWallet.createWallet(wallet);
    }


}
