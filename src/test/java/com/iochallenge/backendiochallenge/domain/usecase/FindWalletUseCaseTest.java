package com.iochallenge.backendiochallenge.domain.usecase;

import com.iochallenge.backendiochallenge.domain.model.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class FindWalletUseCaseTest {

    @Mock
    private FindWallet findWallet;

    @InjectMocks
    private  FindWalletUseCase findWalletUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findRegisteredWallet() {

        Wallet wallet = new Wallet("Wallet");
        wallet.setId(1L);

        when(findWallet.findWallet(anyLong())).thenReturn(wallet);

        Wallet actual = findWalletUseCase.findWallet(1L);

        verify(findWallet, times(1)).findWallet(1L);

        assertEquals(wallet, actual);

    }

    @Test
    void FindNotRegisteredWallet(){
        Wallet wallet = findWalletUseCase.findWallet(anyLong());

        verify(findWallet, times(1)).findWallet(anyLong());

        assertEquals(wallet, null);

    }
}