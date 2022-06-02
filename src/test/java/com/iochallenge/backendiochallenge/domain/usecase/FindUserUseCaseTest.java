package com.iochallenge.backendiochallenge.domain.usecase;

import com.iochallenge.backendiochallenge.domain.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
class FindUserUseCaseTest {

    @Mock
    private FindUser findUser;

    @InjectMocks
    private FindUserUseCase findUserUseCase;

    @Mock
    private CreateUser createUser;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findNotRegisteredUser() {
        //User not registered
        Assert.assertEquals(findUserUseCase.findUser("Mario"), null);

    }

    @Test
    void  findRegisteredUser(){

        User user = createUser.createUser(new User("Mario","123"));

        User actual = findUserUseCase.findUser("Mario");
        Assert.assertEquals(user, actual);

    }
}