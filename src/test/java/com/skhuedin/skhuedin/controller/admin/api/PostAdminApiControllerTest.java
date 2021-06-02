package com.skhuedin.skhuedin.controller.admin.api;

import com.skhuedin.skhuedin.domain.Blog;
import com.skhuedin.skhuedin.domain.Category;
import com.skhuedin.skhuedin.domain.Posts;
import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.dto.posts.PostsSaveRequestDto;
import com.skhuedin.skhuedin.infra.Role;
import com.skhuedin.skhuedin.repository.BlogRepository;
import com.skhuedin.skhuedin.repository.CategoryRepository;
import com.skhuedin.skhuedin.repository.PostsRepository;
import com.skhuedin.skhuedin.service.CategoryService;
import com.skhuedin.skhuedin.service.PostsService;
import com.skhuedin.skhuedin.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@Sql("/truncate.sql")
@SpringBootTest
class PostAdminApiControllerTest {
    private MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    PostsRepository postsRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();

        User user = User.builder()
                .email("user@email.com")
                .password("1234")
                .name("user")
                .userImageUrl("/img")
                .graduationYear("2016")
                .entranceYear("2022")
                .provider(Provider.SELF)
                .build();
        userService.save(user);

        Blog blog = blogRepository.save(Blog.builder()
                .user(user)
                .content("테스트 블로그 컨텐츠")
                .profile(null)
                .build());

        Category category = Category.builder()
                .name("연봉")
                .weight(4L)
                .build();
        categoryRepository.save(category);

        postsRepository.save(Posts.builder()
                .blog(blog)
                .title("책장의 첫 게시글")
                .content("저는 이렇게 저렇게 공부했어요!")
                .category(category)
                .build());
    }

    @DisplayName("'/post'로 get요청")
    @Test
    void postList_page() throws Exception {
        this.mockMvc.perform(get("/post"))
                .andDo(print())
                .andExpect(view().name("contents/postList"))
                .andExpect(status().isOk());
    }

    @DisplayName("'/post'로 post요청")
    @Test
    void postList() throws Exception {

        //given 어떤 값이 주어지고
        User user = User.builder()
                .name("홍길동")
                .email("her08072@naver.com")
                .password("1234")
                .userImageUrl("/img")
                .role(Role.ADMIN)
                .entranceYear("2016")
                .graduationYear("2022")
                .provider(Provider.KAKAO)
                .build();
        Long id = userService.save(user);
        String token = userService.createToken("her08072@naver.com");

        //when 무엇을 했을 때
        MockHttpServletRequestBuilder requestBuilder = post("/post")
                .header("Authorization", "Bearer " + token);

        //then 어떤 값을 원한다.
        MvcResult result = this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..['id']").exists())
                .andReturn();
    }
}