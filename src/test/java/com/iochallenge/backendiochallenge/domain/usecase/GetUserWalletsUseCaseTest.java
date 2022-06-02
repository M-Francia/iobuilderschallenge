package com.iochallenge.backendiochallenge.domain.usecase;

import com.iochallenge.backendiochallenge.domain.model.Wallet;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class GetUserWalletsUseCaseTest {

    @Mock
    private GetUserWallets getUserWallets;

    @InjectMocks
    private GetUserWalletsUseCase getUserWalletsUseCase;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserWallets() {

        List<Wallet> wallets = new ArrayList<Wallet>();
        wallets.add(new Wallet("wallet"));

        when(getUserWallets.getUserWallets(anyLong())).thenReturn(wallets);

        List<Wallet> actual = getUserWalletsUseCase.getUserWallets(anyLong());

        Assert.assertEquals(wallets, actual);

        verify(getUserWallets, times(1)).getUserWallets(anyLong());

    }

    @Test
    void getUserEmptyWallets(){
        List<Wallet> actual = getUserWalletsUseCase.getUserWallets(anyLong());

        Assert.assertEquals(new ArrayList<Wallet>(), actual);
        verify(getUserWallets, times(1)).getUserWallets(anyLong());

    }
}