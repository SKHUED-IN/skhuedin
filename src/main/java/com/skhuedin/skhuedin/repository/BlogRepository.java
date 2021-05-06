package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BlogRepository extends JpaRepository<Blog, Long> {

    @Query("select b from Blog b join b.user u")
    Page<Blog> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"user"})
    @Query("select b " +
            "from Blog b " +
            "join b.posts p " +
            "group by b " +
            "order by sum(p.view) desc")
    Page<Blog> findAllOrderByPostsView(Pageable pageable);

<<<<<<< HEAD
    Boolean existsByUserId(Long id);
=======
    @Query("select b from Blog b where b.user.id = :userId")
    Blog findBlogByUserId(@Param("userId") Long userId);
>>>>>>> b83251a (feat: 불필요한 파일 삭제 + 어드민 페이지 로직 수정)
}