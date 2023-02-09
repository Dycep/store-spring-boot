package com.project.store.service;

import com.project.store.exception.email.EmailIsAlreadyTaken;
import com.project.store.model.ConfirmationToken;
import com.project.store.model.User;
import com.project.store.model.UserRole;
import com.project.store.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceUnitTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ConfirmationTokenService confirmationTokenService;

    private UserService underTest;

    @BeforeEach
    void setUp(){underTest = new UserService(userRepository,passwordEncoder,confirmationTokenService);}

    @Test
    void shouldLoadUserByUsername() {
        User user = new User();
        user.setEmail("email@mail.ru");
        given(userRepository.findUserByEmail(user.getEmail())).willReturn(Optional.of(user));
        underTest.loadUserByUsername(user.getEmail());
        verify(userRepository).findUserByEmail(user.getEmail());
    }

    @Test
    void findUserByEmail() {
        User user = new User();
        user.setEmail("email@mail.ru");
        given(userRepository.findUserByEmail(user.getEmail())).willReturn(Optional.of(user));
        underTest.getUserByEmail(user.getEmail());
        verify(userRepository).findUserByEmail(user.getEmail());
    }

    @Test
    void shouldSignUpUser() {
        User user = new User();
        user.setEmail("email@mail.ru");
        underTest.signUpUser(user);
        ArgumentCaptor<User> argumentCaptor =
                ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(argumentCaptor.capture());
        User capturedUser = argumentCaptor.getValue();
        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    void willThrowEmailIsAlreadyTaken(){
        User user = new User();
        user.setEmail("email@mail.ru");
        given(userRepository.findUserByEmail(user.getEmail()))
                .willReturn(Optional.of(user));
        assertThatThrownBy(()->underTest.signUpUser(user))
                .isInstanceOf(EmailIsAlreadyTaken.class)
                .hasMessageContaining("User with this email exists");
        verify(userRepository, never()).save(user);
    }

    @Test
    void shouldEnableUser() {
        User user = new User();
        user.setEmail("email@mail.ru");
        underTest.enableUser(user.getEmail());
        verify(userRepository).enableUser(user.getEmail());
    }
}