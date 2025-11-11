package com.example.EdufyMusic.configs;

import com.example.EdufyMusic.converters.JwtAuthConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
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

    // ED-265-SJ
    private JwtAuthConverter jwtAuthConverter;

    // ED-266-SJ
    @Value("${genre.service.url}")
    private String genreServiceUrl;

    @Autowired
    public void setJwtAuthConverter (JwtAuthConverter jwtAuthConverter) {
        this.jwtAuthConverter = jwtAuthConverter;
    }

    // ED-39-SJ
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .headers(h -> h.frameOptions(fo -> fo.disable()))
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/h2-console/**").permitAll()
                                .requestMatchers("/music/**").authenticated()
                                .anyRequest().permitAll()
                ) .httpBasic(Customizer.withDefaults()); // TODO change later -> Keycloak
        /*
                .oauth2ResourceServer(oauth2 ->
                        oauth2
                                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter))
                );
         */

        return http.build();
    }

    // DEV for testing endpoints through Postman (ED-261-SJ)
    // TODO change later -> Keycloak
    @Bean
    public UserDetailsService userDetailsService() {
        var user = User.withUsername("user").password("{noop}user").roles("music_user").build();
        var admin = User.withUsername("admin").password("{noop}admin").roles("music_admin").build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}
