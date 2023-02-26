package com.project.store.service;

import com.project.store.exception.token.ConfirmationTokenNotFoundException;
import com.project.store.model.ConfirmationToken;
import com.project.store.repository.ConfirmationTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ConfirmationTokenServiceUnitTest {
    @Mock
    private ConfirmationTokenRepository confirmationTokenRepository;
    private ConfirmationTokenService underTest;

    @BeforeEach
    void setUp() {
        underTest = new ConfirmationTokenService(confirmationTokenRepository);
    }

    @Test
    void shouldSaveConfirmationToken() {
        ConfirmationToken token = new ConfirmationToken();
        underTest.saveConfirmationToken(token);
        ArgumentCaptor<ConfirmationToken> argumentCaptor =
                ArgumentCaptor.forClass(ConfirmationToken.class);
        verify(confirmationTokenRepository).save(argumentCaptor.capture());
        ConfirmationToken capturedToken = argumentCaptor.getValue();
        assertThat(capturedToken).isEqualTo(token);
    }

    @Test
    void shouldGetToken() {
        ConfirmationToken token = new ConfirmationToken();
        token.setToken("token");
        given(confirmationTokenRepository.findByToken(token.getToken()))
                .willReturn(Optional.of(token));
        underTest.getToken(token.getToken());
        verify(confirmationTokenRepository).findByToken("token");
    }

    @Test
    void willThrowConfirmationTokenNotFoundException() {
        assertThatThrownBy(()-> underTest.getToken("token"))
                .isInstanceOf(ConfirmationTokenNotFoundException.class)
                .hasMessage("Token has not been found");
    }

    @Test
    void setConfirmedAt() {
        ConfirmationToken token = new ConfirmationToken();
        token.setToken("token");
        underTest.setConfirmedAt("token");
    }
}