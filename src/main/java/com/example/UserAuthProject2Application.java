package com.example;

import com.example.config.SpringApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class UserAuthProject2Application {
    public static void main(String[] args) {
        SpringApplication.run(UserAuthProject2Application.class, args);
    }
}
