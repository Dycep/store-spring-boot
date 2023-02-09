package com.project.store.model;

import lombok.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

import static javax.persistence.GenerationType.SEQUENCE;


@Entity
@Table(name = "users", uniqueConstraints = {
                @UniqueConstraint(name = "user_email_unique", columnNames = "email"),
                @UniqueConstraint(name = "phone_unique_constraint", columnNames = "phone")})
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString(exclude = "password")
public class User implements UserDetails {
    @Id
    @SequenceGenerator(name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "user_sequence")
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "first_name", nullable = false, columnDefinition = "TEXT", length = 40)
    private String firstName;
    @Column(name = "last_name", nullable = false, columnDefinition = "TEXT", length = 40)
    private String lastName;
    @Column(name = "phone", nullable = false, length = 12)
    private String phone;
    @Column(name = "email", nullable = false, columnDefinition = "TEXT", length = 45)
    private String email;
    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.CUSTOMER;
    @Column
    private String password;
    private Boolean locked = false;
    private Boolean enabled = false;
//    @Column(
//            name = "dob",
//            nullable = false
//    )
//    private LocalDate dob;
    @Transient
    private Integer age;

    public User(String firstName, String lastName, String email, String password, String phone, UserRole userRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.phone = phone;
//        this.dob = dob;
    }


//    public Integer getAge() {
//        return Period.between(this.dob, LocalDate.now()).getYears();
//    }
//
//    public void setAge(Integer age) {
//        this.age = age;
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
