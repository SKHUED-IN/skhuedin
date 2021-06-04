package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.Category;
import com.skhuedin.skhuedin.domain.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c " +
            "from Category c " +
            "where c.weight > 0 " +
            "order by c.weight desc ")
    List<Category> findByWeight();


    @Query("select c " +
            "from Category c " +
            "where c.weight > 0 " +
            "order by c.weight desc ")
    Page<Category> findByWeightPage(Pageable pageable);

    @Query("select c " +
            "from Category c " +
            "order by c.createdDate desc ")
    Page<Category> findByCreatedDate(Pageable pageable);

}