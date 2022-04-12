package com.project.store.service;

import com.project.store.dto.RegistrationRequest;
import com.project.store.email.EmailSender;
import com.project.store.exception.email.EmailAlreadyConfirmedException;
import com.project.store.exception.email.EmailIsNotValidException;
import com.project.store.exception.token.ConfirmationTokenExpiredException;
import com.project.store.model.ConfirmationToken;
import com.project.store.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceUnitTest {

    @Mock
    private UserService userService;
    @Mock
    private ConfirmationTokenService confirmationTokenService;
    @Mock
    private EmailSender emailSender;

    private RegistrationService underTest;

    @BeforeEach
    void setUp() {
        underTest = new RegistrationService(
                userService,
                confirmationTokenService, emailSender
        );
    }

    @Test
    void shouldRegister() {
        User user = new User();
        user.setEmail("email@mail.ru");
        RegistrationRequest request = new RegistrationRequest(
                user.getFirstName(),
                user.getLastName(),
                user.getPassword(),
                user.getEmail(),
                user.getPhone()
        );
        underTest.register(request);
        verify(userService).signUpUser(user);
        verify(emailSender).send(anyString(), anyString());
    }

    @Test
    void willThrowEmailIsNotValidException() {
        User user = new User();
        user.setEmail("email@mail");
        RegistrationRequest request = new RegistrationRequest(
                user.getFirstName(),
                user.getLastName(),
                user.getPassword(),
                user.getEmail(),
                user.getPhone()
        );
        assertThatThrownBy(()->underTest.register(request))
                .isInstanceOf(EmailIsNotValidException.class)
                .hasMessage("email is not valid");
    }

    @Test
    void shouldConfirmToken() {
        ConfirmationToken token = new ConfirmationToken();
        token.setExpiresAt(LocalDateTime.of(2100,12,12,1,1));
        token.setUser(new User());
        given(confirmationTokenService.getToken(token.getToken())).willReturn(token);
        underTest.confirmToken(token.getToken());
        verify(confirmationTokenService).getToken(token.getToken());
        verify(confirmationTokenService).setConfirmedAt(token.getToken());
        verify(userService).enableUser(token.getUser().getEmail());
    }

    @Test
    void willThrowEmailAlreadyConfirmedException() {
        ConfirmationToken token = new ConfirmationToken();
        token.setConfirmedAt(LocalDateTime.now());
        given(confirmationTokenService.getToken(token.getToken())).willReturn(token);
        assertThatThrownBy(()->underTest.confirmToken(token.getToken()))
                .isInstanceOf(EmailAlreadyConfirmedException.class)
                .hasMessage("email already confirmed");
    }

    @Test
    void willThrowConfirmationTokenExpiredException() {
        ConfirmationToken token = new ConfirmationToken();
        token.setUser(new User());
        token.setExpiresAt(LocalDateTime.now());
        given(confirmationTokenService.getToken(token.getToken())).willReturn(token);
        assertThatThrownBy(()->underTest.confirmToken(token.getToken()))
                .isInstanceOf(ConfirmationTokenExpiredException.class)
                .hasMessage("token expired");

    }
}