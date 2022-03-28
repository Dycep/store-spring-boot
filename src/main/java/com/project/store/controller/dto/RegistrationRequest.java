package com.project.store.controller.dto;

import com.project.store.model.User;
import com.project.store.model.UserRole;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class RegistrationRequest {
    private final String firstName;
    private final String lastName;
    private final String password;
    private final String email;
    private final String phone;


}
