package com.jgen.configuration;

import com.jgen.services.CustomAuthenticationSuccessHandler;
import com.jgen.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeRequests(auth ->
                auth
                    .antMatchers("/", "/login", "/assets/**", "/access-denied").permitAll()
                    // Permissions pour SUPERVISOR et ADMIN
                    .antMatchers(
                            "/capitalisations/modification",
                            "/projects/ajout",
                            "/capitalisations/ajout",
                            "/projects/modification"
                    ).hasAnyAuthority("SUPERVISOR", "ADMIN")
                    // Permissions pour ADMIN uniquement
                    .antMatchers(
                            "/projects/suppression",
                            "/capitalisations/suppression",
                            "/users/suppression",
                            "/users/changerole",
                            "/administer",
                            "/changestatus",
                            "/registration"
                    ).hasAuthority("ADMIN")
                    .antMatchers(
                            "/capitalisations",
                            "/**/planification",
                            "**/suivi"
                    ).hasAnyAuthority("ADMIN", "SUPERVISOR", "USER")
                    // Toutes les autres requêtes nécessitent une authentification
                    .anyRequest().authenticated()
            )
            .formLogin(form ->
                form
                    .loginPage("/login")
                        .successHandler(new CustomAuthenticationSuccessHandler()) // Utilisation du handler personnalisé
                        .failureUrl("/?error=true")
                    .permitAll()
            )
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);

        return authenticationManagerBuilder.build();
    }
}

