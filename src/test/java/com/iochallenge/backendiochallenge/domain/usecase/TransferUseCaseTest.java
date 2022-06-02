package com.iochallenge.backendiochallenge.domain.usecase;

import com.iochallenge.backendiochallenge.domain.exception.TransferException;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class TransferUseCaseTest {

    @Mock
    private FindWallet findWallet;

    @InjectMocks
    private TransferUseCase transferUseCase;

    @Mock
    private CreateTransaction createTransaction;

    @Mock
    private UpdateWallet updateWallet;

    private Wallet wallet1;
    private Wallet wallet2;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        wallet1 = new Wallet("from");
        wallet1.setId(1L);
        wallet1.setUserId(1L);
        wallet1.setBalance(BigDecimal.TEN);

        wallet2 = new Wallet("to");
        wallet2.setId(2L);
    }

    @Test
    void transferOk() {

        when(findWallet.findWallet(1L)).thenReturn(wallet1);
        when(findWallet.findWallet(2L)).thenReturn(wallet2);

        transferUseCase.transfer(BigDecimal.ONE, wallet1.getId(), wallet2.getId(), wallet1.getUserId());


        Assert.assertEquals(BigDecimal.TEN.subtract(BigDecimal.ONE), wallet1.getBalance());
        Assert.assertEquals(BigDecimal.ONE, wallet2.getBalance());

        verify(updateWallet, times(1)).updateWallet(wallet2);
        verify(updateWallet, times(1)).updateWallet(wallet1);
    }

    @Test
    void transferWithoutMoney(){
        when(findWallet.findWallet(1L)).thenReturn(wallet1);
        when(findWallet.findWallet(2L)).thenReturn(wallet2);

        Exception exception = assertThrows(TransferException.class, () -> transferUseCase.transfer(BigDecimal.ONE, wallet2.getId(), wallet1.getId(), wallet2.getUserId()));

        assertEquals(wallet2.getId() + " wallet donesn´t have enought amount", exception.getMessage());


    }
}