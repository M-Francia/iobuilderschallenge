package com.iochallenge.backendiochallenge.domain.usecase;

import com.iochallenge.backendiochallenge.domain.model.User;

public class FindUserUseCase {
 
    private final FindUser findUser;

    public FindUserUseCase(FindUser findUser) {
        this.findUser = findUser;
    }

    public User findUser(String username){
        return findUser.findUser(username);
    }

    
}
