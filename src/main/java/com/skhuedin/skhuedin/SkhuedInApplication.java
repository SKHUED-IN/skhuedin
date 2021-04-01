package com.skhuedin.skhuedin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SkhuedInApplication {

    public static void main(String[] args) {

        SpringApplication.run(SkhuedInApplication.class, args);
    }
}