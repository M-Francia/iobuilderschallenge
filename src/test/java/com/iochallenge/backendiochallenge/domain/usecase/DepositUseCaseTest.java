package com.iochallenge.backendiochallenge.domain.usecase;

import com.iochallenge.backendiochallenge.domain.model.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class DepositUseCaseTest {

    @InjectMocks
    private DepositUseCase depositUseCase;

    @Mock
    private  FindWallet findWallet;

    @Mock
    private CreateTransaction createTransaction;

    @Mock
    private UpdateWallet updateWallet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deposit() {

        Wallet wallet = new Wallet("name");

        when(findWallet.findWallet(anyLong())).thenReturn(wallet);

        depositUseCase.deposit(BigDecimal.TEN, anyLong());

        verify(updateWallet, times(1)).updateWallet(wallet);

        assertEquals(wallet.getBalance(),BigDecimal.TEN);

    }
}