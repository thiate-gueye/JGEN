package com.jgen;

import com.jgen.models.Users;
import com.jgen.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class JgenApplication {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public static void main(String[] args) {
        SpringApplication.run(JgenApplication.class, args);
    }

    @Bean
    CommandLineRunner runner() {
        return args -> {
            if (usersRepository.countUsers() < 1) {
                Users users = new Users();
                users.setNom("YADE");
                users.setPrenom("Maimouna");
                users.setUsername("admin@jgen.sn");
                users.setPassword(passwordEncoder.encode("MayaPasser"));
                users.setRole("ADMIN");
                users.setActif(true);
                usersRepository.save(users);
            }
        };
    }

}
