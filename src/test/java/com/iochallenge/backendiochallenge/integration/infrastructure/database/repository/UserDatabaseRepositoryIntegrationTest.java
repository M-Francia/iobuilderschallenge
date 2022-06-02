package com.iochallenge.backendiochallenge.integration.infrastructure.database.repository;

import com.iochallenge.backendiochallenge.infrastructure.database.model.User;
import com.iochallenge.backendiochallenge.infrastructure.database.repository.UserDatabaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class UserDatabaseRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserDatabaseRepository userDatabaseRepository;

    @Test
    void findByUsername() {

        User entityUser = new User("mario", "123");
        entityManager.persist(entityUser);
        entityManager.flush();
        User byUsername = userDatabaseRepository.findByUsername(entityUser.getUsername()).orElse(null);
        assertEquals(entityUser, byUsername);

    }

    @Test
    public void findByUsernameAndPassword() {
        // given
        User entityUser = new User("mario", "123");
        entityManager.persist(entityUser);
        entityManager.flush();
        User byUsernameAndPassword = userDatabaseRepository.findByUsername(entityUser.getUsername()).orElse(null);
        assertEquals(entityUser, byUsernameAndPassword);
    }

    @Test
    public void findByUsernameAndPasswordFails() {
        // given
        User entityUser = new User("mario", "123");
        entityManager.persist(entityUser);
        entityManager.flush();
        User byUsernameAndPassword = userDatabaseRepository.findByUsernameAndPassword("mari","1234");
        assertEquals(byUsernameAndPassword, null);
    }

}