package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostsRepository extends JpaRepository<Posts, Long> {

    List<Posts> findByBlogIdOrderByLastModifiedDateDesc(Long blogId);

    @Query("select p from Posts p where p.blog.id = :blogId")
    List<Posts> findPostsByBlogId(@Param("blogId") Long blogId);

    @Query("select p from Posts p where p.category.id = :categoryId")
    List<Posts> findPostsByCategoryId(@Param("categoryId") Long categoryId);

    @Query("select p " +
            "from Posts p " +
            "where p.category.id = :categoryId " +
            "order by p.view desc, p.lastModifiedDate")
    List<Posts> findByCategoryIdOrderByView(@Param("categoryId") Long categoryId, Pageable pageable);

    @Query("select p " +
            "from Posts p " +
            "where p.blog.id = :blogId and p.deleteStatus = :deleteStatus " +
            "order by p.lastModifiedDate")
    Page<Posts> findByBlogId(@Param("blogId") Long blogId, boolean deleteStatus, Pageable pageable);

    /* admin 전용 */
    @EntityGraph(attributePaths = {"blog", "blog.user", "blog.profile", "category"})
    @Query("select p " +
            "from Posts p " +
            "where p.category.name not like '건의사항' " +
            "order by p.lastModifiedDate DESC")
    Page<Posts> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"blog", "blog.user", "blog.profile", "category"})
    @Query("select p " +
            "from Posts p " +
            "where p.blog.user.name like %:username% and p.category.name not like '건의사항' " +
            "order by p.lastModifiedDate ")
    Page<Posts> findByUserName(Pageable pageable, @Param("username") String username);

    @EntityGraph(attributePaths = {"blog", "blog.user", "blog.profile", "category"})
    @Query("select p " +
            "from Posts p " +
            "where p.category.name like %:categoryName% and p.category.name not like '건의사항' " +
            "order by p.lastModifiedDate ")
    Page<Posts> findByCategoryName(Pageable pageable, @Param("categoryName") String categoryName);

    @EntityGraph(attributePaths = {"blog", "blog.user", "blog.profile", "category"})
    @Query("select p " +
            "from Posts p " +
            "where p.id = :postsId")
    Optional<Posts> findById(@Param("postsId") Long id);

    @EntityGraph(attributePaths = {"blog", "blog.user", "blog.profile", "category"})
    @Query("select p " +
            "from Posts p " +
            "where p.category.name like '건의사항' " +
            "order by p.lastModifiedDate desc")
    Page<Posts> findBySuggestions(Pageable pageable);
}