package com.iochallenge.backendiochallenge.domain.usecase;

import com.iochallenge.backendiochallenge.domain.model.User;


import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class GetUserUseCase {
    
    private final GetUser getUser;
    private final FindUser findUser;
    

    public GetUserUseCase(GetUser getUser, FindUser findUser) {
        this.getUser = getUser;
        this.findUser = findUser;
    }


    public User getUser(String username, String password){
        
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        
        User user = findUser.findUser(username);
        if( user != null){
            String passwordHashed = user.getPassword();
            if(argon2.verify(passwordHashed, password.toCharArray())){
                return getUser.getUser(username, passwordHashed);
            }

        }
        return null;
    }

}
