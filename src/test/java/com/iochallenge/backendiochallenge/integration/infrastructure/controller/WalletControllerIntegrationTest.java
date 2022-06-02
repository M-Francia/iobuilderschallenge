package com.iochallenge.backendiochallenge.integration.infrastructure.controller;

import com.iochallenge.backendiochallenge.domain.model.Transaction;
import com.iochallenge.backendiochallenge.domain.model.Wallet;
import com.iochallenge.backendiochallenge.domain.usecase.*;
import com.iochallenge.backendiochallenge.infrastructure.controller.WalletController;
import com.iochallenge.backendiochallenge.utils.JWTUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(WalletController.class)
class WalletControllerIntegrationTest {

    private static final String PATH ="/wallet";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JWTUtil jwtUtil;

    @MockBean
    private CreateWalletUseCase createWalletUseCase;

    @MockBean
    private GetWalletTransactionsUseCase getWalletTransactionsUseCase;

    @MockBean
    private FindWalletUseCase findWalletUseCase;

    @MockBean
    private DepositUseCase depositUseCase;

    @MockBean
    private TransferUseCase transferUseCase;

    private Wallet wallet;

    @BeforeEach
    void setUp() {
        wallet = new Wallet("wallet");
        wallet.setId(1L);
        wallet.setUserId(1L);
    }

    @Test
    void createWallet() throws Exception {

        when(jwtUtil.logged(anyString(),anyString())).thenReturn(true);
        when(createWalletUseCase.createWallet(anyLong(), anyString())).thenReturn(wallet);

        this.mockMvc.perform(post(PATH)
                        .param("user_id", "1")
                        .param("name", "wallet")
                        .header("token", "token"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", Matchers.is("wallet")))
                .andExpect(jsonPath("$.id",Matchers.is(1)))
                .andExpect(jsonPath("$.balance",Matchers.is(0)));

        verify(createWalletUseCase, times(1)).createWallet(anyLong(),anyString());
    }

    @Test
    void createWalletNotRegisteredUser() throws Exception {

        this.mockMvc.perform(post(PATH)
                        .param("user_id", "1")
                        .param("name", "wallet"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void createWalletBadParams() throws Exception{

        when(jwtUtil.logged(anyString(),anyString())).thenReturn(true);
        when(createWalletUseCase.createWallet(anyLong(), anyString())).thenReturn(null);

        this.mockMvc.perform(post(PATH)
                        .header("token", "token"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getWallet() throws Exception {

        when(findWalletUseCase.findWallet(anyLong())).thenReturn(wallet);
        when(jwtUtil.logged(anyString(),anyString())).thenReturn(true);

        this.mockMvc.perform(get(PATH + "/1")
                        .param("id", "1")
                        .header("token", "token"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("wallet")))
                .andExpect(jsonPath("$.id",Matchers.is(1)))
                .andExpect(jsonPath("$.balance",Matchers.is(0)));

    }

    @Test
    void getWalletNotRegisteredWallet() throws Exception{

        when(findWalletUseCase.findWallet(anyLong())).thenReturn(null);
        when(jwtUtil.logged(anyString(),anyString())).thenReturn(true);

        this.mockMvc.perform(get(PATH + "/" + wallet.getId())
                        .param("id", "1")
                        .header("token", "token"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void deposit() throws Exception {

        when(depositUseCase.deposit(any(BigDecimal.class), anyLong())).thenReturn(true);

        this.mockMvc.perform(put(PATH + "/" + wallet.getId() + "/deposit")
                        .param("amount", "10")
                )
                .andExpect(status().isOk());
    }

    @Test
    void depositNotRegisteredWallet() throws Exception{

        when(depositUseCase.deposit(any(BigDecimal.class), anyLong())).thenReturn(false);

        this.mockMvc.perform(put(PATH + "/" + wallet.getId() + "/deposit")
                        .param("amount", "10"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void createTransfer() throws Exception {

        when(jwtUtil.logged(anyString(),anyString())).thenReturn(true);
        when(transferUseCase.transfer(any(BigDecimal.class),anyLong(), anyLong(), anyLong())).thenReturn(true);

        this.mockMvc.perform(post(PATH + "/" + wallet.getId() + "/transfer")
                        .param("id", "1")
                        .param("toWalletId", "2")
                        .param("user_id", "1")
                        .param("amount", "1")
                        .header("token", "token"))
                .andDo(print())
                .andExpect(status().isOk());


    }

    @Test
    void createTransferToNotRegisteredWallet() throws Exception {

        when(jwtUtil.logged(anyString(),anyString())).thenReturn(true);
        when(transferUseCase.transfer(any(BigDecimal.class),anyLong(), anyLong(), anyLong())).thenReturn(false);

        this.mockMvc.perform(post(PATH + "/" + wallet.getId() + "/transfer")
                        .param("id", "1")
                        .param("toWalletId", "2")
                        .param("user_id", "1")
                        .param("amount", "1")
                        .header("token", "token"))
                .andExpect(status().is4xxClientError());


    }

    @Test
    void getTransactions() throws Exception {
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction("description", BigDecimal.ONE);
        transactions.add(transaction);

        when(findWalletUseCase.findWallet(anyLong())).thenReturn(wallet);
        when(jwtUtil.logged(anyString(),anyString())).thenReturn(true);
        when(getWalletTransactionsUseCase.getWalletTransactionsUseCase(anyLong())).thenReturn(transactions);

        this.mockMvc.perform(get(PATH + "/" + wallet.getId() + "/transactions")
                        .param("id", "1")
                        .header("token", "token"))
                .andDo(print())
                .andExpect(status().isOk())//Add also date
                .andExpect(jsonPath("$[0].amount",Matchers.is(1)))
                .andExpect(jsonPath("$[0].description",Matchers.is(transaction.getDescription())));
    }

    @Test
    void getTransactionsOfNotRegisteredWallet() throws Exception{

        when(findWalletUseCase.findWallet(anyLong())).thenReturn(null);

        this.mockMvc.perform(get(PATH + "/" + wallet.getId() + "/transactions")
                        .param("id", "1")
                        .header("token", "token"))
                .andExpect(status().is4xxClientError());
    }

}