package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.Blog;
import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql("/truncate.sql")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BlogRepository blogRepository;

    User user;
    Blog blog;

    @BeforeEach
    void setUp() {
        user = generateUser();
        blog = generateBlog(user);

        userRepository.save(user);
        blogRepository.save(blog);
    }

    @Test
    @DisplayName("email을 활용하여 유저를 조회하는 테스트")
    void findByEmail() {

        // given
        String email = "user@email.com";

        // when
        User user = userRepository.findByEmail(email).get();

        // then
        assertAll(
                () -> assertEquals(email, user.getEmail()),
                () -> assertEquals(blog, user.getBlog())
        );
    }

    User generateUser() {
        return User.builder()
                .email("user@email.com")
                .name("user")
                .userImageUrl("/img")
                .provider(Provider.SELF)
                .build();
    }

    private Blog generateBlog(User user) {
        return Blog.builder()
                .user(user)
                .content(user.getName() + "의 책장 ")
                .build();
    }
}