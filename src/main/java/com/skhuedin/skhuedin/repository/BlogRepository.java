package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {
}