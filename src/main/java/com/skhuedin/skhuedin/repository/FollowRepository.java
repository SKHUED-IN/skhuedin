package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    List<Follow> findByToUserId(Long id);
}