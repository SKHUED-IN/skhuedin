package com.skhuedin.skhuedin.domain;

import com.skhuedin.skhuedin.domain.blog.Blog;
import com.skhuedin.skhuedin.domain.posts.Posts;
import com.skhuedin.skhuedin.domain.user.Provider;
import com.skhuedin.skhuedin.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostsTest {

    private Posts posts;

    @BeforeEach
    void beforeEach() {
        User user = User.builder()
                .email("user@email.com")
                .name("user")
                .userImageUrl("/img")
                .provider(Provider.SELF)
                .build();

        Blog blog = Blog.builder()
                .user(user)
                .content("테스트 블로그 컨텐츠")
                .build();

        posts = Posts.builder()
                .blog(blog)
                .title("책장의 첫 게시글")
                .content("저는 이렇게 저렇게 공부했어요!")
                .category(null)
                .build();
    }

    @Test
    @DisplayName("조회수를 3 증가 시키는 테스트")
    void addView() {

        // given & when
        posts.addView();
        posts.addView();
        posts.addView();

        // then
        assertEquals(posts.getView(), 3);
    }
}