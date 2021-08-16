package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.user.Role;
import com.skhuedin.skhuedin.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    /* admin 전용 */
    Page<User> findAll(Pageable pageable);

    @Query("select u " +
            "from User u " +
            "where u.name like %:username% " +
            "order by u.lastModifiedDate ")
    Page<User> findByUserName(Pageable pageable, @Param("username") String username);

    @Query("select u " +
            "from User u " +
            "where u.role = :role ")
    Page<User> findByRole(Pageable pageable, @Param("role") Role role);
}