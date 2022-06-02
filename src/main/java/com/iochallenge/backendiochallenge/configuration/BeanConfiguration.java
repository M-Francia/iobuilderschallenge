package com.iochallenge.backendiochallenge.configuration;

import com.iochallenge.backendiochallenge.domain.usecase.*;
import com.iochallenge.backendiochallenge.infrastructure.database.DatabaseProvider;
import com.iochallenge.backendiochallenge.infrastructure.database.repository.TransactionDatabaseRepository;
import com.iochallenge.backendiochallenge.infrastructure.database.repository.UserDatabaseRepository;
import com.iochallenge.backendiochallenge.infrastructure.database.repository.WalletDatabaseRepository;
import com.iochallenge.backendiochallenge.utils.JWTUtil;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public DatabaseProvider userDatabaseProvider(UserDatabaseRepository userDatabaseRepository, WalletDatabaseRepository walletDatabaseRepository, TransactionDatabaseRepository transactionDatabaseRepository){
        return new DatabaseProvider(userDatabaseRepository, walletDatabaseRepository, transactionDatabaseRepository);
    }

    @Bean
    public SignUpUserUseCase signUpUserUseCase(CreateUser createUser, FindUser findUser){
        return new SignUpUserUseCase(createUser, findUser);
    }

    @Bean
    public GetUserUseCase getUserUseCase(GetUser getUser, FindUser findUser){
        return new GetUserUseCase(getUser, findUser);
    }

    @Bean
    public FindUserUseCase findUserUseCase(FindUser findUser){
        return new FindUserUseCase(findUser);
    }

    @Bean 
    public CreateWalletUseCase createWalletUseCase(CreateWallet createWallet, FindUserById findUserById){
        return new CreateWalletUseCase(createWallet, findUserById);
    }

    @Bean
    public GetUserWalletsUseCase getUserWalletsUseCase(GetUserWallets getUserWallets, FindUserById findUserById){
        return new GetUserWalletsUseCase(getUserWallets, findUserById);
    }

    @Bean
    public FindWalletUseCase findWalletUseCase(FindWallet findWallet){
        return new FindWalletUseCase(findWallet);
    }

    @Bean 
    public DepositUseCase depositUseCase(FindWallet findWallet, UpdateWallet updateWallet, CreateTransaction createTransaction){
        return new DepositUseCase(findWallet, updateWallet, createTransaction);
    }

    @Bean
    public TransferUseCase transferUseCase (FindWallet findWallet, UpdateWallet updateWallet, CreateTransaction createTransaction){
        return new TransferUseCase(findWallet, updateWallet, createTransaction);
    }

    @Bean
    public GetWalletTransactionsUseCase getWalletTransactionsUseCase (GetWalletTransactions getWalletTransactions){
        return new GetWalletTransactionsUseCase(getWalletTransactions);
    }

    @Bean
    public JWTUtil jwtUtil (){
        return new JWTUtil();
    }


    
}
