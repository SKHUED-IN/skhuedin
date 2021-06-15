package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.Blog;
import com.skhuedin.skhuedin.domain.Category;
import com.skhuedin.skhuedin.domain.Posts;
import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql("/truncate.sql")
class PostsRepositoryTest {

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostsRepository postsRepository;

    @Autowired
    CategoryRepository categoryRepository;

    private Blog blog;
    private Category category1;
    private Category category2;

    @BeforeEach
    void beforeEach() {
        User user = User.builder()
                .email("user@email.com")
                .password("1234")
                .name("user")
                .userImageUrl("/img")
                .graduationYear("2016")
                .entranceYear("2022")
                .provider(Provider.SELF)
                .build();

        userRepository.save(user);

        blog = Blog.builder()
                .user(user)
                .content("테스트 블로그 컨텐츠")
                .profile(null)
                .build();

        blogRepository.save(blog);

        category1 = Category.builder()
                .name("category1")
                .weight(10L)
                .build();

        category2 = Category.builder()
                .name("category2")
                .weight(20L)
                .build();

        categoryRepository.save(category1);
        categoryRepository.save(category2);
    }

    @Test
    @DisplayName("categoryId 별로 최신 update 및 최대 조회수 순으로 limit 3로 정렬하여 조회하는 테스트")
    void findByCategoryIdOrderByView() {

        // given
        Posts posts1 = generatePosts(1, category1);
        Posts posts2 = generatePosts(1, category1);
        Posts posts3 = generatePosts(1, category1);

        posts1.addView();

        posts2.addView();
        posts2.addView();

        posts3.addView();
        posts3.addView();
        posts3.addView();

        postsRepository.save(posts1);
        postsRepository.save(posts2);
        postsRepository.save(posts3);

        // when
        PageRequest pageRequest = PageRequest.of(0, 3);
        List<Posts> posts = postsRepository.findByCategoryIdOrderByView(category1.getId(), pageRequest);

        // then
        assertEquals(posts.size(), 3);
    }

    @Test
    @DisplayName("blog id로 paging 처리하여 조회하는 테스트")
    void findByBlogIdPaging() {

        // given
        for (int i = 0; i < 10; i++) {
            Posts posts = generatePosts(i);
            postsRepository.save(posts);
        }

        // when
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<Posts> page = postsRepository.findByBlogId(blog.getId(), false, pageRequest);

        // then
        assertAll(
                () -> assertEquals(page.getContent().size(), 5), // 조회된 데이터 수
                () -> assertEquals(page.getTotalElements(), 10), // 전체 데이터 수
                () -> assertEquals(page.getNumber(), 0), // 페이지 번호
                () -> assertEquals(page.getTotalPages(), 2), // 전체 페이지 번호
                () -> assertTrue(page.isFirst()), // 첫 번째 페이지 t/f
                () -> assertTrue(page.hasNext()) // 다음 페이지 t/f
        );
    }

    Posts generatePosts(int index, Category category) {
        return Posts.builder()
                .blog(blog)
                .title("책장의 게시글 " + index)
                .content("저는 이렇게 저렇게 공부했어요!")
                .category(category)
                .build();
    }

    Posts generatePosts(int index) {
        return Posts.builder()
                .blog(blog)
                .title("책장의 게시글 " + index)
                .content("저는 이렇게 저렇게 공부했어요!")
                .category(null)
                .build();
    }


    @AfterEach
    void afterEach() {
        postsRepository.deleteAll();
        blogRepository.deleteAll();
        userRepository.deleteAll();
    }
}