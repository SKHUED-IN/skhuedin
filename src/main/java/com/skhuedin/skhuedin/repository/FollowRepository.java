package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
}
