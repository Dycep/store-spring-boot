package com.project.store.repository;

import com.project.store.model.User;
import com.project.store.model.UserRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }


    @Test
    void shouldFindUserByEmail() {
        User user = new User("firstName",
                "lastName",
                "email@mail.ru",
                "password",
                "375297529368",
                UserRole.CUSTOMER);

        underTest.save(user);

        boolean expected = underTest.findUserByEmail("email@mail.ru").isPresent();

        assertThat(expected).isTrue();
    }

    @Test
    void shouldEnableUser() {

    }
}