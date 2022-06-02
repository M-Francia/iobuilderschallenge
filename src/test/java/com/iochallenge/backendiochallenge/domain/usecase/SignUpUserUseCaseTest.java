package com.iochallenge.backendiochallenge.domain.usecase;

import com.iochallenge.backendiochallenge.domain.exception.UserAlreadyExistsException;
import com.iochallenge.backendiochallenge.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class SignUpUserUseCaseTest {

    @Mock
    private FindUser findUser;

    @Mock
    private  CreateUser createUser;

    @InjectMocks
    private SignUpUserUseCase signUpUserUseCase;

    @BeforeEach
    void SetUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void signUpUser() {
        //Mario signup
        when(findUser.findUser("Mario")).thenReturn(null);

        User user = new User("Mario", "123456");
        when(createUser.createUser(any(User.class))).thenReturn(user);

        User actual = signUpUserUseCase.signUpUser("Mario", "123456");

        assertEquals(user, actual);

    }

    @Test
    public void signupUserAlreadyExists(){
        when(findUser.findUser("Mario")).thenReturn(new User("Mario", "123456"));

        Exception exception = assertThrows(UserAlreadyExistsException.class, () -> signUpUserUseCase.signUpUser("Mario", "123456"));

        assertEquals("Username Mario already exists", exception.getMessage());
    }
}