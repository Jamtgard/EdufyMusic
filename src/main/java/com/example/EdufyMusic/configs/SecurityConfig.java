package com.example.EdufyMusic.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

// ED-39-SJ
@Configuration
@EnableWebSecurity
public class SecurityConfig {

/*
    // ED-39-SJ
    // Activate later for PROD
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .headers(h -> h.frameOptions(fo -> fo.disable()))
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/h2-console/**").permitAll()
                                .anyRequest().permitAll() //change later to Authenticated
                );
        /*
                .oauth2ResourceServer(oauth2 ->
                        oauth2
                                .jwt(jwt -> jwt.jwtAuthenticationConverter())
                );
          //
        return http.build();
    }
 */

    // DEV for testing endpoints through Postman (ED-261-SJ)
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/music/**").authenticated()
                        .anyRequest().permitAll()
                )
                .httpBasic(basic -> {});

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        var user = User.withUsername("user").password("{noop}user").roles("USER").build();
        var admin = User.withUsername("admin").password("{noop}admin").roles("ADMIN").build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}
