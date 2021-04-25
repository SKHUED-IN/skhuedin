package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {

    List<Posts> findByBlogIdOrderByLastModifiedDateDesc(Long blogId);
}