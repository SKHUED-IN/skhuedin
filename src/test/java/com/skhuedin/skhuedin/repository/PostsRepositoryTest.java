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

    @Test
    @DisplayName("blog id로 posts를 조회하는 테스트")
    void findByBlogId() {

        // given
        for (int i = 0; i < 10; i++) {
            Posts posts = generatePosts(i);
            postsRepository.save(posts);
        }

        // when
        List<Posts> posts = postsRepository.findByBlogId(blog.getId());

        // then
        assertEquals(posts.size(), 10);
    }

    @Test
    @DisplayName("등록한 user 이름을 활용하여 조회하는 테스트")
    void findByUserName() {

        // given
        for (int i = 0; i < 10; i++) {
            Posts posts = generatePosts(i);
            postsRepository.save(posts);
        }

        // when
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Posts> postsList = postsRepository.findByUserName(pageRequest, "user");

        // then
        assertEquals(postsList.getContent().size(), 10);
    }

    @Test
    @DisplayName("등록한 category name을 활용하여 조회하는 테스트")
    void findByCategoryName() {

        // given
        for (int i = 0; i < 10; i++) {
            Posts posts = generatePosts(i);
            postsRepository.save(posts);
        }

        // when
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Posts> postsList = postsRepository.findByCategoryName(pageRequest, "category1");

        // then
        assertEquals(postsList.getContent().size(), 10);
    }

    @Test
    @DisplayName("suggestions을 조회하는 테스트")
    void findSuggestions() {

        // given
        Category suggestions = Category.builder()
                .name("건의사항")
                .weight(0L)
                .build();
        categoryRepository.save(suggestions);

        for (int i = 0; i < 10; i++) {
            Posts posts = generatePosts(i, suggestions);
            postsRepository.save(posts);
        }

        // when
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Posts> posts = postsRepository.findSuggestions(pageRequest);

        // then
        assertEquals(posts.getContent().size(), 10);
    }

    @Test
    @DisplayName("category id 별로 count 조회하는 테스트")
    void countByCategoryId() {
        
        // given
        Category suggestions = Category.builder()
                .name("건의사항")
                .weight(0L)
                .build();
        categoryRepository.save(suggestions);

        for (int i = 0; i < 10; i++) {
            Posts posts = generatePosts(i, suggestions);
            postsRepository.save(posts);
        }

        // when
        PageRequest pageRequest = PageRequest.of(0, 10);
        Long count = postsRepository.countByCategoryId(suggestions.getId());

        // then
        assertEquals(count, 10);
    }

    @Test
    @DisplayName("최대 길이의 content를 저장하는 테스트")
    void save_max_content() {

        // given
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 50000; i++) {
            stringBuilder.append("+");
        }

        Posts posts = Posts.builder()
                .blog(blog)
                .title("책장의 게시글")
                .content(stringBuilder.toString())
                .category(category1)
                .build();

        Posts save = postsRepository.save(posts);

        // when
        Posts findPosts = postsRepository.findById(save.getId()).get();

        // then
        assertEquals(findPosts.getContent().length(), 50000);
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
                .category(category1)
                .build();
    }

    @AfterEach
    void afterEach() {
        postsRepository.deleteAll();
        blogRepository.deleteAll();
        userRepository.deleteAll();
    }
}