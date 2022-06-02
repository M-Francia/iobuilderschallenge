package com.iochallenge.backendiochallenge.integration.infrastructure.controller;

import com.iochallenge.backendiochallenge.domain.model.User;
import com.iochallenge.backendiochallenge.domain.usecase.GetUserUseCase;
import com.iochallenge.backendiochallenge.infrastructure.controller.AuthController;
import com.iochallenge.backendiochallenge.utils.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(AuthController.class)
class AuthControllerIntegrationTest {

    private static final String PATH = "/login";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetUserUseCase getUserUseCase;

    @MockBean
    private JWTUtil jwtUtil;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("mario", "123");
        user.setId(1L);
    }

    @Test
    void login() throws Exception {

        when(getUserUseCase.getUser(anyString(),anyString())).thenReturn(user);
        when(jwtUtil.logged(anyString(), anyString())).thenReturn(true);

        this.mockMvc.perform(post(PATH)
                .param("username", user.getUsername())
                .param("password", user.getPassword()))
                .andExpect(status().isOk());

    }

    @Test
    void invalidLogin() throws Exception {

        when(getUserUseCase.getUser(anyString(),anyString())).thenReturn(null);

        this.mockMvc.perform(post(PATH)
                        .param("username", user.getUsername())
                        .param("password", user.getPassword()))
                .andExpect(status().is4xxClientError());

    }
}