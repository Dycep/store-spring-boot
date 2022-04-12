package com.project.store.security;

import com.project.store.model.UserRole;
import com.project.store.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.project.store.model.UserRole.ADMIN;
import static com.project.store.model.UserRole.CUSTOMER;
import static org.springframework.http.HttpMethod.*;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/**","/registration/**", "/css/*", "/js/*").permitAll()//TODO replace /** matcher with /
                    .antMatchers(POST,"/cart").hasRole(CUSTOMER.name())
                    .antMatchers(GET,"/*").permitAll()
                    .antMatchers(POST, "/*").hasRole(ADMIN.name())
                    .antMatchers(PUT, "/*").hasRole(ADMIN.name())
                    .antMatchers(DELETE, "/*").hasRole(ADMIN.name())
                .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .and()
                .rememberMe()
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder.encode("password"))
                .roles(ADMIN.name());
        ;
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userService);

        return provider;
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("password"))
                .roles(ADMIN.name())
                .build();

        return new InMemoryUserDetailsManager(
                adminUser
        );
    }
}


