package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.Posts;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.infra.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndPassword(String email, String password);


// admin
    Page<User> findAll(Pageable pageable);

    @Query("select u " +
            "from User u " +
            "where u.name like %:username% " +
            "order by u.lastModifiedDate ")
    Page<User> findByUserName(Pageable pageable, @Param("username") String username);


    @Query("select u " +
            "from User u " +
            "where u.role =com.skhuedin.skhuedin.infra.Role.USER ")
    Page<User> findByRoleUser(Pageable pageable);

    @Query("select u " +
            "from User u " +
            "where u.role = com.skhuedin.skhuedin.infra.Role.ADMIN ")
    Page<User> findByRoleAdmin(Pageable pageable);
}