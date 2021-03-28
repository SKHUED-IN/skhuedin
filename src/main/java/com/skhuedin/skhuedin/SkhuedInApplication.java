package com.skhuedin.skhuedin;

import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.repository.UserRepository;
import com.skhuedin.skhuedin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.transaction.Transactional;

@SpringBootApplication
@EnableJpaAuditing
public class SkhuedInApplication {


    public static void main(String[] args) {
        SpringApplication.run(SkhuedInApplication.class, args);




    }
}



