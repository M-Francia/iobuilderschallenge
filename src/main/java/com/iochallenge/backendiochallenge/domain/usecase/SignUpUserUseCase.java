package com.iochallenge.backendiochallenge.domain.usecase;


import com.iochallenge.backendiochallenge.domain.exception.UserAlreadyExistsException;
import com.iochallenge.backendiochallenge.domain.model.User;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class SignUpUserUseCase {

    private final CreateUser createUser;

    private final FindUser findUser;

    public SignUpUserUseCase(CreateUser createUser, FindUser findUser) {
        this.createUser = createUser;
        this.findUser = findUser;
    }
    
    public User signUpUser(String username, String password){

        User user = findUser.findUser(username);
        if(user != null){
            throw new UserAlreadyExistsException("Username " + username + " already exists");
        }
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String passwordHashed = argon2.hash(1, 1024, 1, password.toCharArray());
        User newUser = new User(username, passwordHashed);
        return createUser.createUser(newUser);
    }
    
    
    
}
