package com.iochallenge.backendiochallenge.infrastructure.database.repository;




import java.util.Optional;

import com.iochallenge.backendiochallenge.infrastructure.database.model.User;


import org.springframework.data.repository.CrudRepository;

public interface UserDatabaseRepository extends CrudRepository <User, Long>{

    public Optional<User> findByUsername(String username);
    public User findByUsernameAndPassword(String username, String password);
    
}
