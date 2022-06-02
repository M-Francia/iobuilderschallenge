package com.iochallenge.backendiochallenge.infrastructure.database;


import java.util.List;
import java.util.stream.Collectors;

import com.iochallenge.backendiochallenge.domain.model.Transaction;
import com.iochallenge.backendiochallenge.domain.model.User;
import com.iochallenge.backendiochallenge.domain.model.Wallet;
import com.iochallenge.backendiochallenge.domain.usecase.*;
import com.iochallenge.backendiochallenge.infrastructure.database.repository.TransactionDatabaseRepository;
import com.iochallenge.backendiochallenge.infrastructure.database.repository.UserDatabaseRepository;
import com.iochallenge.backendiochallenge.infrastructure.database.repository.WalletDatabaseRepository;



public class DatabaseProvider implements CreateUser, FindUser, GetUser, CreateWallet, FindWallet, GetUserWallets, UpdateWallet, CreateTransaction, GetWalletTransactions, FindUserById {

    private final UserDatabaseRepository userDatabaseRepository;
    private final WalletDatabaseRepository walletDatabaseRepository;
    private final TransactionDatabaseRepository transactionDatabaseRepository;

    public DatabaseProvider(UserDatabaseRepository userDatabaseRepository, WalletDatabaseRepository walletDatabaseRepository, TransactionDatabaseRepository transactionDatabaseRepository) {
        this.userDatabaseRepository = userDatabaseRepository;
        this.walletDatabaseRepository = walletDatabaseRepository;
        this.transactionDatabaseRepository = transactionDatabaseRepository;
    }


    @Override
    public User getUser(String name, String password) {
        return mapUser(userDatabaseRepository.findByUsernameAndPassword(name, password));
    }

    //db to domain
    private User mapUser(com.iochallenge.backendiochallenge.infrastructure.database.model.User user){
        if(user != null) {
            User uReturn = new User(user.getUsername(), user.getPassword());
            uReturn.setId(user.getId());
            return uReturn;
        }
        return null;
    }

    private Transaction mapTransaction(com.iochallenge.backendiochallenge.infrastructure.database.model.Transaction transaction){
        if(transaction != null){
            Transaction transactionReturn = new Transaction(transaction.getDescription(), transaction.getAmount());
            transactionReturn.setCreateAt(transaction.getCreateAt());
            transactionReturn.setId(transaction.getId());
            transactionReturn.setWalletId(transaction.getWallet().getId());
            return transactionReturn;
        }
        return null;
    }


    private Wallet mapWallet(com.iochallenge.backendiochallenge.infrastructure.database.model.Wallet wallet){
        if(wallet != null){
            Wallet walletReturn = new Wallet(wallet.getName());
            walletReturn.setUserId(wallet.getUser().getId());
            walletReturn.setBalance(wallet.getBalance());
            walletReturn.setId(wallet.getId());
            //walletReturn.setTransactions(mapTransactions(wallet.getTransactions()));
            return walletReturn;
        }
        return null;
    }


    private com.iochallenge.backendiochallenge.infrastructure.database.model.Wallet mapWallet(Wallet wallet){
        if(wallet != null){
            com.iochallenge.backendiochallenge.infrastructure.database.model.Wallet walletReturn = new com.iochallenge.backendiochallenge.infrastructure.database.model.Wallet(wallet.getName());
            com.iochallenge.backendiochallenge.infrastructure.database.model.User userDb = userDatabaseRepository.findById(wallet.getUserId()).orElse(null);
            walletReturn.setUser(userDb);
            walletReturn.setId(wallet.getId());
            walletReturn.setBalance(wallet.getBalance());
            walletReturn.setId(wallet.getId());
            return walletReturn;
        }
        return null;
    }


    @Override
    public User findUser(String username) {
        return mapUser(userDatabaseRepository.findByUsername(username).orElse(null));
    }


    @Override
    public User createUser(User user) {
        com.iochallenge.backendiochallenge.infrastructure.database.model.User newUser = 
        new com.iochallenge.backendiochallenge.infrastructure.database.model.User(user.getUsername(), user.getPassword());

        return mapUser(userDatabaseRepository.save(newUser));
    }


    @Override
    public Wallet createWallet(Wallet wallet) {
        //Refactor: the logic must be in the domain
        com.iochallenge.backendiochallenge.infrastructure.database.model.User userDb =  userDatabaseRepository.findById(wallet.getUserId()).orElse(null);
        if (userDb != null){
            com.iochallenge.backendiochallenge.infrastructure.database.model.Wallet walletDb = new com.iochallenge.backendiochallenge.infrastructure.database.model.Wallet(wallet.getName());
            walletDb.setUser(userDb);
            return mapWallet(walletDatabaseRepository.save(walletDb));
        }
        return null;

    }


    @Override
    public Wallet findWallet(Long id) {
        return mapWallet(walletDatabaseRepository.findById(id).orElse(null));
    }


    @Override
    public  void updateWallet(Wallet wallet) {
         walletDatabaseRepository.save(mapWallet(wallet));
    }


    @Override
    public List<Wallet> getUserWallets(Long user_id) {
        //Refactor:
        com.iochallenge.backendiochallenge.infrastructure.database.model.User userDb = userDatabaseRepository.findById(user_id).orElse(null);
        if (userDb != null){
            List<Wallet> wallets = userDb.getWallets().stream().map(wallet -> mapWallet(wallet)).collect(Collectors.toList());
            return wallets;
        }
        return null;
    }


    @Override
    public Transaction createTransaction(Transaction transaction) {
        com.iochallenge.backendiochallenge.infrastructure.database.model.Transaction newTransaction = new com.iochallenge.backendiochallenge.infrastructure.database.model.Transaction(transaction.getDescription(), transaction.getAmount());
        com.iochallenge.backendiochallenge.infrastructure.database.model.Wallet walletDb = walletDatabaseRepository.findById(transaction.getWalletId()).orElse(null);
        newTransaction.setWallet(walletDb);
        return mapTransaction(transactionDatabaseRepository.save(newTransaction));
    }


    @Override
    public List<Transaction> getWalletTransactions(Long id) {
        //Refactor
        com.iochallenge.backendiochallenge.infrastructure.database.model.Wallet walletDb = walletDatabaseRepository.findById(id).orElse(null);
        List<Transaction> transactions = walletDb.getTransactions().stream().map(transaction -> mapTransaction(transaction)).collect(Collectors.toList());
        if (walletDb != null){
            return transactions;
        }
        return null;
    }


    @Override
    public User findUserById(Long id) {
        return mapUser(userDatabaseRepository.findById(id).orElse(null));
    }
}
