package com.iochallenge.backendiochallenge.domain.usecase;

import java.util.List;

import com.iochallenge.backendiochallenge.domain.model.User;
import com.iochallenge.backendiochallenge.domain.model.Wallet;

public class GetUserWalletsUseCase {


    private final GetUserWallets getUserWallets;
    private  final FindUserById findUserById;

    

    public GetUserWalletsUseCase(GetUserWallets getUserWallets, FindUserById findUserById) {
        this.getUserWallets = getUserWallets;
        this.findUserById = findUserById;
    }



    public List<Wallet> getUserWallets(Long user_id){
        //Refactor
        return getUserWallets.getUserWallets(user_id);
    }


    
    
}
