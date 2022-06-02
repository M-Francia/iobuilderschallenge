package com.iochallenge.backendiochallenge.infrastructure.database.repository;


import com.iochallenge.backendiochallenge.infrastructure.database.model.Wallet;

import org.springframework.data.repository.CrudRepository;

public interface WalletDatabaseRepository extends CrudRepository<Wallet, Long> {
    
}
