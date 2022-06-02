package com.iochallenge.backendiochallenge.domain.usecase;

import com.iochallenge.backendiochallenge.domain.model.Transaction;
import com.iochallenge.backendiochallenge.domain.model.Wallet;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class GetWalletTransactionsUseCaseTest {

    @Mock
    private GetWalletTransactions getWalletTransactions;

    @InjectMocks
    private GetWalletTransactionsUseCase getWalletTransactionsUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getWalletTransactionsUseCase() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("descr", BigDecimal.ONE));

        when(getWalletTransactions.getWalletTransactions(anyLong())).thenReturn(transactions);

        List<Transaction> actual =  getWalletTransactionsUseCase.getWalletTransactionsUseCase(1L);

        Assert.assertEquals(transactions, actual);

    }
}