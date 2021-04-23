package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail (String email);
    Optional<User> findByName (String name);

    @Modifying    // update  Query
    @Query("update User u set u.token = ?2 WHERE u.email = ?1")
    void update(String email, String token);
}