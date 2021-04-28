package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.Blog;
import com.skhuedin.skhuedin.domain.Posts;
import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PostsRepositoryTest {

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostsRepository postsRepository;

    private Blog blog;

    @BeforeEach
    void beforeEach() {
        User user = User.builder()
                .email("user@email.com")
                .password("1234")
                .name("user")
                .userImageUrl("/img")
                .graduationYear(LocalDateTime.now())
                .entranceYear(LocalDateTime.now())
                .provider(Provider.SELF)
                .build();

        userRepository.save(user);

        blog = Blog.builder()
                .user(user)
                .content("테스트 블로그 컨텐츠")
                .profileImageUrl("/img")
                .build();

        blogRepository.save(blog);
    }

    @Test
    @DisplayName("blog id 별 posts 목록을 수정날짜 내림차순으로 조회하는 테스트")
    void findByBlogId() {

        // given
        Posts posts1 = Posts.builder()
                .blog(blog)
                .title("책장의 첫 게시글")
                .content("저는 이렇게 저렇게 공부했어요!")
                .category(null)
                .build();

        Posts posts2 = Posts.builder()
                .blog(blog)
                .title("책장의 첫 게시글")
                .content("저는 이렇게 저렇게 공부했어요!")
                .category(null)
                .build();

        postsRepository.save(posts1);
        postsRepository.save(posts2);

        // when
        List<Posts> posts = postsRepository.findByBlogIdOrderByLastModifiedDateDesc(blog.getId());

        // then
        assertAll(
                () -> assertEquals(posts.get(0).getLastModifiedDate()
                        .compareTo(posts.get(1).getLastModifiedDate()), 1)
        );
    }

    @AfterEach
    void afterEach() {
        postsRepository.deleteAll();
        blogRepository.deleteAll();
        userRepository.deleteAll();
    }
}