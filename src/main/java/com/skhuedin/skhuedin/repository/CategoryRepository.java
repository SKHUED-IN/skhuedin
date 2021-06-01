package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c " +
            "from Category c " +
            "where c.weight > 0 " +
            "order by c.weight desc ")
    List<Category> findByWeight();
}