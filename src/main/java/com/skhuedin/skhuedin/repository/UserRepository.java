package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.user.Role;
import com.skhuedin.skhuedin.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {"blog"})
    @Query("select u " +
            "from User u " +
            "where u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @EntityGraph(attributePaths = {"blog"})
    @Query("select u " +
            "from User u " +
            "where u.id = :id")
    Optional<User> findById(@Param("id") Long id);

    /* admin 전용 */
    Page<User> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"blog"})
    @Query("select u " +
            "from User u " +
            "where u.name like %:username% " +
            "order by u.lastModifiedDate ")
    Page<User> findByUserName(Pageable pageable, @Param("username") String username);

    @EntityGraph(attributePaths = {"blog"})
    @Query("select u " +
            "from User u " +
            "where u.role = :role ")
    Page<User> findByRole(Pageable pageable, @Param("role") Role role);
}