package com.iochallenge.backendiochallenge.integration.infrastructure.controller;

import com.iochallenge.backendiochallenge.domain.exception.UserAlreadyExistsException;
import com.iochallenge.backendiochallenge.domain.model.User;
import com.iochallenge.backendiochallenge.domain.model.Wallet;
import com.iochallenge.backendiochallenge.domain.usecase.FindUserUseCase;
import com.iochallenge.backendiochallenge.domain.usecase.GetUserWalletsUseCase;
import com.iochallenge.backendiochallenge.domain.usecase.SignUpUserUseCase;
import com.iochallenge.backendiochallenge.infrastructure.controller.UserController;
import com.iochallenge.backendiochallenge.utils.JWTUtil;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
class UserControllerIntegrationTest {

    private static final String PATH = "/user";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FindUserUseCase findUserUseCase;

    @MockBean
    private SignUpUserUseCase signUpUserUseCase;

    @MockBean
    private GetUserWalletsUseCase getUserWalletsUseCase;

    @MockBean
    private JWTUtil jwtUtil;

    private User user;

    @BeforeEach
    void setUp(){
        user = new User("mario", "123");
        user.setId(1L);
    }

    @Test
    void getRegisteredUser() throws Exception {

        when(jwtUtil.logged(anyString(),anyString())).thenReturn(true);
        when(findUserUseCase.findUser(anyString())).thenReturn(user);

        this.mockMvc.perform(get(PATH + "/" + user.getUsername())
                        .header("token","token")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.username", Matchers.is(user.getUsername())));

        verify(findUserUseCase, times(1)).findUser("mario");

    }

    @Test
    void getNotRegisteredUser() throws Exception{

        when(jwtUtil.logged(anyString(),anyString())).thenReturn(true);

        this.mockMvc.perform(get(PATH + "/" + user.getUsername())
                        .header("token","token")
                )
                .andDo(print())
                .andExpect(status().is4xxClientError());

    }

    @Test
    void signUpUser() throws Exception{

        when(signUpUserUseCase.signUpUser(anyString(), anyString())).thenReturn(user);

        this.mockMvc.perform(post(PATH)
                .param("username","mario")
                .param("password", "123")
            )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.username", Matchers.is(user.getUsername())));

    }

    @Test
    void signUpUserAlreadyExists() throws Exception {

        //Chequear
        when(signUpUserUseCase.signUpUser(anyString(),anyString())).thenThrow(UserAlreadyExistsException.class);

        this.mockMvc.perform(post(PATH)).andExpect(status().is4xxClientError());

    }

    @Test
    void getWallets() throws Exception {
        Wallet wallet = new Wallet("wallet");
        wallet.setId(1L);
        user.addWallet(wallet);

        when(jwtUtil.logged(anyString(),anyString())).thenReturn(true);
        when(getUserWalletsUseCase.getUserWallets(anyLong())).thenReturn(user.getWallets());


        this.mockMvc.perform(get(PATH + "/" + user.getId() + "/wallets")
                .header("token","token")
            )
                .andDo(print())
                .andExpect(jsonPath("$[0].name",Matchers.is("wallet")))
                .andExpect(jsonPath("$[0].id",Matchers.is(1)))
                .andExpect(jsonPath("$[0].balance",Matchers.is(0)))
                .andExpect(status().isOk());

        verify(getUserWalletsUseCase, times(1)).getUserWallets(user.getId());
    }

    @Test
    void getWalletsNotLoggedUser() throws Exception{

        this.mockMvc.perform(get(PATH + "/" + user.getId() + "/wallets")
                .header("token","token")
        ).andExpect(status().is4xxClientError());

    }
}