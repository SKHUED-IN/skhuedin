package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.user.Provider;
import com.skhuedin.skhuedin.domain.user.Role;
import com.skhuedin.skhuedin.domain.user.User;
import com.skhuedin.skhuedin.dto.blog.BlogMainResponseDto;
import com.skhuedin.skhuedin.dto.blog.BlogSaveRequestDto;
import com.skhuedin.skhuedin.error.exception.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql("/truncate.sql")
class BlogServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    BlogService blogService;

    static User generateUser(String email, String name) {
        return User.builder()
                .email(email)
                .name(name)
                .provider(Provider.SELF)
                .userImageUrl("/images/user.png")
                .role(Role.ROLE_USER)
                .build();
    }

    static BlogSaveRequestDto generateBlogSaveRequestDto(Long userId) {
        return BlogSaveRequestDto.builder()
                .userId(userId)
                .content("test blog")
                .build();
    }

    static MultipartFile generateMultipartFile() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File("src/test/resources/test.txt"));
        return new MockMultipartFile("test", fileInputStream);
    }

    @Test
    @DisplayName("BlogSaveRequestDto??? ???????????? Blog ????????? ???????????? ???????????? ????????? - ??????")
    void saveNewBlog() throws IOException {

        // given
        User user = generateUser("user@email.com", "user");
        Long saveId = userService.save(user);

        BlogSaveRequestDto blogSaveRequestDto = generateBlogSaveRequestDto(saveId);

        // when
        Long blogId = blogService.save(blogSaveRequestDto, generateMultipartFile());
        BlogMainResponseDto responseDto = blogService.findById(blogId);

        // then
        assertAll(
                () -> assertEquals(blogId, responseDto.getId()),
                () -> assertEquals(user.getId(), responseDto.getUser().getId())
        );
    }

    @Test
    @DisplayName("BlogSaveRequestDto??? ???????????? Blog ????????? ???????????? n??? ?????? ???????????? ????????? - ??????")
    void saveAgainBlog() throws IOException {

        // given
        User user = generateUser("user@email.com", "user");
        Long saveId = userService.save(user);

        BlogSaveRequestDto blogSaveRequestDto = generateBlogSaveRequestDto(saveId);
        blogService.save(blogSaveRequestDto, generateMultipartFile());

        // when & then
        assertThrows(DataIntegrityViolationException.class,
                () -> blogService.save(blogSaveRequestDto, generateMultipartFile())
        );
    }

    @Test
    @DisplayName("BlogSaveRequestDto??? ???????????? Blog ????????? ???????????? ????????? - ??????")
    @Transactional
    void updateBlog() throws IOException {

        // given
        User user = generateUser("user@email.com", "user");
        Long saveId = userService.save(user);

        BlogSaveRequestDto blogSaveRequestDto = generateBlogSaveRequestDto(saveId);
        blogService.save(blogSaveRequestDto, generateMultipartFile());

        BlogSaveRequestDto updateDto = BlogSaveRequestDto.builder()
                .userId(saveId)
                .content("????????? ????????????")
                .build();

        // when
        Long blogId = blogService.update(updateDto, generateMultipartFile());
        BlogMainResponseDto responseDto = blogService.findById(blogId);

        // then
        assertAll(
                () -> assertEquals(blogId, responseDto.getId()),
                () -> assertEquals(updateDto.getUserId(), responseDto.getUser().getId())
        );
    }

    @Test
    @DisplayName("blog id??? ???????????? blog??? ???????????? ????????? - ??????")
    void delete() throws IOException {

        // given
        User user = generateUser("user@email.com", "user");
        Long saveId = userService.save(user);

        BlogSaveRequestDto blogSaveRequestDto = generateBlogSaveRequestDto(saveId);
        Long blogId = blogService.save(blogSaveRequestDto, generateMultipartFile());

        // when
        blogService.delete(blogId);

        // then
        assertThrows(EntityNotFoundException.class, () ->
                blogService.findById(blogId)
        );
    }

    @Test
    @DisplayName("???????????? ?????? blog id??? ????????? ???????????? ????????? - ??????")
    void deleteWithNotExistsBlog() {

        // given & when & then
        assertThrows(EntityNotFoundException.class, () ->
                blogService.findById(0L)
        );
    }

    @Test
    @DisplayName("blog id??? ???????????? blog??? ???????????? ?????????")
    void findById() throws IOException {

        // given
        User user = generateUser("user@email.com", "user");
        Long saveId = userService.save(user);

        BlogSaveRequestDto blogSaveRequestDto = generateBlogSaveRequestDto(saveId);
        Long blogId = blogService.save(blogSaveRequestDto, generateMultipartFile());

        // when
        BlogMainResponseDto blogMainResponseDto = blogService.findById(blogId);

        // then
        assertAll(
                () -> assertEquals(blogId, blogMainResponseDto.getId()),
                () -> assertEquals(blogSaveRequestDto.getUserId(), blogMainResponseDto.getUser().getId()),
                () -> assertEquals(blogSaveRequestDto.getContent(), blogMainResponseDto.getContent())
        );
    }
}