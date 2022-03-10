package com.project.store.service;

import com.project.store.model.User;
import com.project.store.registration.token.ConfirmationToken;
import com.project.store.registration.token.ConfirmationTokenService;
import com.project.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MESSAGE = "User with email %s not found";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ConfirmationTokenService confirmationTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.confirmationTokenService = confirmationTokenService;
    }

    public Long getAuthId(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }
    public String getAuthPhone(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getPhone();
    }

//    public List<User> getAllUsers(User user){
//        return userRepository.findAll();
//    }
//
//    public void registerNewUser(User user){
//        Optional<User> optionalUser = userRepository.findUserByEmail(user.getEmail());
//        if(optionalUser.isPresent()){
//            throw new IllegalStateException("email taken");
//        }
//        userRepository.save(user);
//    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email).orElseThrow(()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, email)));
    }

    public String signUpUser(User user){
        boolean userExists = userRepository.findUserByEmail(user.getEmail())
                .isPresent();
        if(userExists){
            throw new IllegalStateException("user with this email exists");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);
        userRepository.save(user);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        //TODO : send email

        return token;
    }

    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }
}
