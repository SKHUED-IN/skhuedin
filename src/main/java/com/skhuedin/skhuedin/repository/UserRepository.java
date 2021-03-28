package com.skhuedin.skhuedin.repository;


import com.skhuedin.skhuedin.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
