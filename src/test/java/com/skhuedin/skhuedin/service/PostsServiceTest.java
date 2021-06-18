package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.Blog;
import com.skhuedin.skhuedin.domain.Category;
import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.dto.posts.PostsMainResponseDto;
import com.skhuedin.skhuedin.dto.posts.PostsSaveRequestDto;
import com.skhuedin.skhuedin.repository.BlogRepository;
import com.skhuedin.skhuedin.repository.CategoryRepository;
import com.skhuedin.skhuedin.repository.PostsRepository;
import com.skhuedin.skhuedin.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql("/truncate.sql")
class PostsServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    PostsRepository postsRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    PostsService postsService;

    private Blog blog;

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

        Category category1 = Category.builder()
                .name("자기소개")
                .weight(1L)
                .build();

        Category category2 = Category.builder()
                .name("학교생활")
                .weight(1L)
                .build();

        Category category3 = Category.builder()
                .name("졸업 후 현재")
                .weight(1L)
                .build();

        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);
    }

    @Test
    @DisplayName("dto 를 받아 posts 를 저장하고 조회하는 테스트")
    void save() {

        // given
        PostsSaveRequestDto requestDto = generateDto();

        // when
        Long saveId = postsService.save(requestDto);
        PostsMainResponseDto responseDto = postsService.findById(saveId);

        // then
        assertAll(
                () -> assertEquals(responseDto.getId(), saveId),
                () -> assertEquals(responseDto.getBlogId(), requestDto.getBlogId()),
                () -> assertEquals(responseDto.getTitle(), requestDto.getTitle()),
                () -> assertEquals(responseDto.getContent(), requestDto.getContent())
        );
    }

    @Test
    @DisplayName("존재하지 않는 blog 로 인하여 저장하던 중 예외를 던지는 테스트")
    void save_false() {

        // given
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .blogId(0L)
                .title("책장의 첫 게시글")
                .content("저는 이렇게 저렇게 공부했어요!")
                .build();

        // when & then
        assertThrows(IllegalArgumentException.class, () ->
                postsService.save(requestDto)
        );
    }

    @Test
    @DisplayName("blog 를 갱신하고 조회하는 테스트")
    void update() {

        // given
        PostsSaveRequestDto requestDto = generateDto();

        PostsSaveRequestDto updateDto = PostsSaveRequestDto.builder()
                .blogId(blog.getId())
                .title("수정된 책장의 첫 게시글")
                .content("저는 이렇게 저렇게 재밌게 공부했어요!")
                .build();

        // when
        Long saveId = postsService.save(requestDto);
        Long updateId = postsService.update(saveId, updateDto);
        PostsMainResponseDto responseDto = postsService.findById(updateId);

        // then
        assertAll(
                () -> assertEquals(updateId, saveId),
                () -> assertEquals(responseDto.getBlogId(), updateDto.getBlogId()),
                () -> assertEquals(responseDto.getTitle(), updateDto.getTitle()),
                () -> assertEquals(responseDto.getContent(), updateDto.getContent())
        );
    }

    @Test
    @DisplayName("posts 를 갱신하던 중 존재하지 않는 posts 로 인하여 예외를 던지는 테스트")
    void update_false() {

        // given
        PostsSaveRequestDto requestDto = generateDto();

        PostsSaveRequestDto updateDto = PostsSaveRequestDto.builder()
                .blogId(blog.getId()) // 존재하지 않는 blog
                .title("수정된 책장의 첫 게시글")
                .content("저는 이렇게 저렇게 재밌게 공부했어요!")
                .build();

        // when
        Long saveId = postsService.save(requestDto);

        // then
        assertThrows(IllegalArgumentException.class, () ->
                postsService.update(0L, updateDto)
        );

    }

    @Test
    @DisplayName("posts 를 갱신하던 중 존재하지 않는 blog 로 인하여 예외를 던지는 테스트")
    void update_blog_false() {

        // given
        PostsSaveRequestDto requestDto = generateDto();

        PostsSaveRequestDto updateDto = PostsSaveRequestDto.builder()
                .blogId(0L) // 존재하지 않는 blog
                .title("수정된 책장의 첫 게시글")
                .content("저는 이렇게 저렇게 재밌게 공부했어요!")
                .build();

        // when & then
        Long saveId = postsService.save(requestDto);
        assertThrows(IllegalArgumentException.class, () ->
                postsService.update(saveId, updateDto)
        );
    }

    @Test
    @DisplayName("posts 를 삭제하는 테스트")
    void delete() {

        // given
        PostsSaveRequestDto requestDto = generateDto();
        Long saveId = postsService.save(requestDto);

        // when
        postsService.delete(saveId);

        // then
        assertEquals(postsRepository.findAll().size(), 0);
    }

    @Test
    @DisplayName("존재하지 않는 posts 를 삭제하여 예외를 던지는 테스트")
    void delete_false() {

        // given & when & then
        assertThrows(IllegalArgumentException.class, () ->
                postsService.delete(0L)
        );
    }

    @Test
    @DisplayName("blog id 로 blog 에 소속된 posts 를 조회하는 테스트")
    void findByBlogId() {

        // given
        for (int i = 0; i < 10; i++) {
            PostsSaveRequestDto requestDto = generateDto();
            postsService.save(requestDto);
        }

        // when
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<PostsMainResponseDto> posts = postsService.findByBlogId(blog.getId(), pageRequest);

        // then
        assertAll(
                () -> assertEquals(posts.getContent().size(), 5), // 조회된 데이터 수
                () -> assertEquals(posts.getTotalElements(), 10), // 전체 데이터 수
                () -> assertEquals(posts.getNumber(), 0), // 페이지 번호
                () -> assertEquals(posts.getTotalPages(), 2), // 전체 페이지 번호
                () -> assertTrue(posts.isFirst()), // 첫 번째 페이지 t/f
                () -> assertTrue(posts.hasNext()) // 다음 페이지 t/f
        );
    }

    @Test
    @DisplayName("posts 의 조회수를 3 증가 시키는 테스트")
    void addView() {

        // given
        PostsSaveRequestDto requestDto = generateDto();
        Long saveId = postsService.save(requestDto);

        // when
        postsService.addView(saveId);
        postsService.addView(saveId);
        postsService.addView(saveId);

        PostsMainResponseDto responseDto = postsService.findById(saveId);

        // then
        assertEquals(responseDto.getView(), 3);
    }

    private PostsSaveRequestDto generateDto() {
        return PostsSaveRequestDto.builder()
                .blogId(blog.getId())
                .title("책장의 첫 게시글")
                .content("저는 이렇게 저렇게 공부했어요!")
                .build();
    }

    @AfterEach
    void afterEach() {
        postsRepository.deleteAll();
        blogRepository.deleteAll();
        userRepository.deleteAll();
    }
}