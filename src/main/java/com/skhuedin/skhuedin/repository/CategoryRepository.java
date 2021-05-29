package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
