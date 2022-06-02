package com.iochallenge.backendiochallenge.domain.usecase;

import com.iochallenge.backendiochallenge.domain.model.User;
import de.mkammerer.argon2.Argon2;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class GetUserUseCaseTest {

    @InjectMocks
    private  GetUserUseCase getUserUseCase;

    @Mock
    private FindUser findUser;

    @Mock
    private GetUser getUser;

    @Mock
    private Argon2 argon2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /*@Test
    void getUser() {

        //DO
        User user = new User("Mario", "123");

        when(argon2.verify(anyString(), anyString().toCharArray())).thenReturn(true);
        when(findUser.findUser(anyString())).thenReturn(user);
        when(getUser.getUser(anyString(), anyString())).thenReturn(user);

        User actual = getUserUseCase.getUser("Mario","123");

        Assert.assertEquals(user, actual);

    }*/

    @Test
    void getNotRegisteredUser(){

        Assert.assertEquals(getUserUseCase.getUser("Mario", "123"), null);

    }
}