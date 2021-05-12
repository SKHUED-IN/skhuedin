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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql("/truncate.sql")
class BlogRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    PostsRepository postsRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 10; i++) {
            User user = generateUser(i);
            userRepository.save(user);
        }
    }
    
    @Test
    @DisplayName("findAll 메소드를 fetch join 하여 조회하는 테스트")
    void findAllFetch() {
        
        // given
        List<User> users = userRepository.findAll();
        for (int i = 0; i < 10; i++) {
            Blog blog = generateBlog(users.get(i), i);
            blogRepository.save(blog);

            for (int j = 0; j < 5; j++) {
                Posts posts = generatePosts(blog, j);
                posts.addView();
                posts.addView();
                blog.addPosts(posts);
                postsRepository.save(posts);
            }
        }

        // when
        List<Blog> blogs = blogRepository.findAllFetch();

        // then
        assertEquals(blogs.size(), 10);
    }

    @Test
    @DisplayName("findAll 메소드를 fetch join 하고 paging 하여 조회하는 테스트")
    // WARN 14288 --- [           main] o.h.h.internal.ast.QueryTranslatorImpl   :
    // HHH000104: firstResult/maxResults specified with collection fetch; applying in memory!
    void findAllFetchPaging() {

        // given
        List<User> users = userRepository.findAll();
        for (int i = 0; i < 10; i++) {
            Blog blog = generateBlog(users.get(i), i);
            blogRepository.save(blog);

            for (int j = 0; j < 5; j++) {
                Posts posts = generatePosts(blog, j);
                posts.addView();
                posts.addView();
                blog.addPosts(posts);
                postsRepository.save(posts);
            }
        }

        // when
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<Blog> page = blogRepository.findAllFetchPaging(pageRequest);

        // then
        assertEquals(page.getContent().size(), 5); // 조회된 데이터 수
        assertEquals(page.getTotalElements(), 10); // 전체 데이터 수
        assertEquals(page.getNumber(), 0); // 페이지 번호
        assertEquals(page.getTotalPages(), 2); // 전체 페이지 번호
        assertTrue(page.isFirst()); // 첫 번째 페이지 t/f
        assertTrue(page.hasNext()); // 다음 페이지 t/f
    }

    @Test
    @DisplayName("findAll 메소드를 blog 의 각 posts 의 조회수 합을 기준으로 정렬하여 조회하는 테스트")
    void findAllOrderByPostsView() {

        // given
        List<User> users = userRepository.findAll();
        User user1 = users.get(0);
        User user2 = users.get(1);

        Blog blog1 = generateBlog(user1, 1);
        Blog blog2 = generateBlog(user2, 2);
        blogRepository.save(blog1);
        blogRepository.save(blog2);

        Posts posts1 = generatePosts(blog1, 1);
        Posts posts2 = generatePosts(blog1, 2);
        posts1.addView();
        posts1.addView();
        posts1.addView();
        posts2.addView();
        posts2.addView();
        postsRepository.save(posts1);
        postsRepository.save(posts2);
        // blog1의 total 조회수 5

        Posts posts3 = generatePosts(blog2, 3);
        Posts posts4 = generatePosts(blog2, 4);
        posts3.addView();
        posts3.addView();
        posts4.addView();
        postsRepository.save(posts3);
        postsRepository.save(posts4);
        // blog2의 total 조회수 3

        // when
        em.flush();
        em.clear();

        List<Blog> blogs = blogRepository.findAllOrderByPostsView();

        // then
        assertAll(
                () -> assertEquals(blogs.get(0).getId(), blog1.getId()),
                () -> assertEquals(blogs.get(1).getId(), blog2.getId())
        );
    }

    @Test
    @DisplayName("findAll 메소드를 blog 의 각 posts 의 조회수 합을 기준으로 정렬하고 paging 하여 조회하는 테스트")
    void findAllOrderByPostsViewPaging() {

        // given
        // given
        List<User> users = userRepository.findAll();
        User user1 = users.get(0);
        User user2 = users.get(1);
        User user3 = users.get(2);

        Blog blog1 = generateBlog(user1, 1);
        Blog blog2 = generateBlog(user2, 2);
        Blog blog3 = generateBlog(user3, 3);
        blogRepository.save(blog1);
        blogRepository.save(blog2);
        blogRepository.save(blog3);

        Posts posts1 = generatePosts(blog1, 1);
        Posts posts2 = generatePosts(blog1, 2);
        posts1.addView();
        posts1.addView();
        posts1.addView();
        posts2.addView();
        posts2.addView();
        postsRepository.save(posts1);
        postsRepository.save(posts2);
        // blog1의 total 조회수 5

        Posts posts3 = generatePosts(blog2, 3);
        Posts posts4 = generatePosts(blog2, 4);
        posts3.addView();
        posts3.addView();
        posts4.addView();
        postsRepository.save(posts3);
        postsRepository.save(posts4);
        // blog2의 total 조회수 3

        Posts posts5 = generatePosts(blog3, 5);
        Posts posts6 = generatePosts(blog3, 6);
        posts5.addView();
        posts5.addView();
        posts5.addView();
        posts5.addView();
        posts5.addView();
        posts6.addView();
        postsRepository.save(posts5);
        postsRepository.save(posts6);
        // blog3의 total 조회수 6

        // when
        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<Blog> page = blogRepository.findAllOrderByPostsViewPaging(pageRequest);

        // then
        assertEquals(page.getContent().size(), 2); // 조회된 데이터 수
        assertEquals(page.getTotalElements(), 3); // 전체 데이터 수
        assertEquals(page.getNumber(), 0); // 페이지 번호
        assertEquals(page.getTotalPages(), 2); // 전체 페이지 번호
        assertTrue(page.isFirst()); // 첫 번째 페이지 t/f
        assertTrue(page.hasNext()); // 다음 페이지 t/f
    }

    User generateUser(int index) {
        return User.builder()
                .email("user" + index + "@email.com")
                .password("1234")
                .name("user" + index)
                .userImageUrl("/img")
                .graduationYear(LocalDateTime.now())
                .entranceYear(LocalDateTime.now())
                .provider(Provider.SELF)
                .build();
    }

    private Blog generateBlog(User user, int index) {
        return Blog.builder()
                .user(user)
                .content(user.getName() + "의 책장 " + index)
                .profileImageUrl("/img")
                .build();
    }

    private Posts generatePosts(Blog blog, int index) {
        return Posts.builder()
                .blog(blog)
                .title(index + " 의 게시글")
                .content("저는 이렇게 저렇게 공부했어요!")
                .category(null)
                .build();
    }

    @AfterEach
    void cleanAll() {
        postsRepository.deleteAll();
        blogRepository.deleteAll();
        userRepository.deleteAll();
    }
}