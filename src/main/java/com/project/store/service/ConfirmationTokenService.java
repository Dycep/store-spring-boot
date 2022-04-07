package com.project.store.service;

import com.project.store.exception.token.ConfirmationTokenNotFoundException;
import com.project.store.model.ConfirmationToken;
import com.project.store.repository.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;


    public void saveConfirmationToken(ConfirmationToken token){
        confirmationTokenRepository.save(token);
    }

    public ConfirmationToken getToken(String token){
        return confirmationTokenRepository.findByToken(token)
                .orElseThrow(()->new ConfirmationTokenNotFoundException("Token has not been found"));
    }

    public void setConfirmedAt(String token) {
        confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }
}
