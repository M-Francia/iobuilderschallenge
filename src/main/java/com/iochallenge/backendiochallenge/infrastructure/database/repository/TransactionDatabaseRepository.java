package com.iochallenge.backendiochallenge.infrastructure.database.repository;

import com.iochallenge.backendiochallenge.infrastructure.database.model.Transaction;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionDatabaseRepository extends CrudRepository <Transaction, Long>{

}
