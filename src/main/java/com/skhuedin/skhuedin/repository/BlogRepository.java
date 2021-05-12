package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {

    @EntityGraph(attributePaths = {"posts", "user"})
    @Query("select distinct b from Blog b")
    List<Blog> findAllFetch();

//    @EntityGraph(attributePaths = {"posts", "user"})
    @EntityGraph(attributePaths = {"user"})
    @Query("select distinct b from Blog b")
    Page<Blog> findAllFetchPaging(Pageable pageable);

    @EntityGraph(attributePaths = {"user"})
    @Query("select b " +
            "from Blog b " +
            "join b.posts p " +
            "group by b " +
            "order by sum(p.view) desc")
    List<Blog> findAllOrderByPostsView();

    @EntityGraph(attributePaths = {"user"})
    @Query("select b " +
            "from Blog b " +
            "join b.posts p " +
            "group by b " +
            "order by sum(p.view) desc")
    Page<Blog> findAllOrderByPostsViewPaging(Pageable pageable);
}