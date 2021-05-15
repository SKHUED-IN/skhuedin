package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BlogRepository extends JpaRepository<Blog, Long> {

    @EntityGraph(attributePaths = {"user"})
    @Query("select distinct b from Blog b join b.user u")
    Page<Blog> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"user"})
    @Query("select b " +
            "from Blog b " +
            "join b.posts p " +
            "group by b " +
            "order by sum(p.view) desc")
    Page<Blog> findAllOrderByPostsView(Pageable pageable);
}