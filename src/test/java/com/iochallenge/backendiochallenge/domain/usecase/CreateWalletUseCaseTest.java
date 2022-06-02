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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
class CreateWalletUseCaseTest {

    @InjectMocks
    private CreateWalletUseCase createWalletUseCase;

    @Mock
    private CreateWallet createWallet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createWallet() {
        Wallet wallet = new Wallet("name");

        when(createWallet.createWallet(any(Wallet.class))).thenReturn(wallet);

        Wallet actual = createWalletUseCase.createWallet(any(Long.class), "name");

        Assert.assertEquals(wallet, actual);

    }
}