package com.skhuedin.skhuedin.controller.admin.api;

import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.infra.Role;
import com.skhuedin.skhuedin.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
class UserAdminApiControllerTest {
    private MockMvc mockMvc;

    @Autowired
    UserService userService;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @DisplayName("Get으로 userList 호출")
    @Test
    void postList_page() throws Exception {
        this.mockMvc.perform(get("/userList"))
                .andDo(print())
                .andExpect(view().name("contents/userList"))
                .andExpect(status().isOk());
    }

    @DisplayName("'/userList'로 post요청")
    @Test
    void userList() throws Exception {

        //given 어떤 값이 주어지고
        String token = getUser();

        //when 무엇을 했을 때
        MockHttpServletRequestBuilder requestBuilder = post("/userList")
                .header("Authorization", "Bearer " + token);

        //then 어떤 값을 원한다.
        MvcResult result = this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..['role']").exists())
                .andReturn();
    }

    @DisplayName("'권한 변경 요청")
    @Test
    void user_role_true() throws Exception {

        //given 어떤 값이 주어지고
        User user = getUserContent();
        Long id = userService.save(user);
        String token = userService.createToken("her08072@naver.com");

        //when 무엇을 했을 때
        MockHttpServletRequestBuilder requestBuilder = post("/user/role")
                .param("id", String.valueOf(id))
                .header("Authorization", "Bearer " + token);

        //then 어떤 값을 원한다.
        MvcResult result = this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        User user1 = userService.getUser(id);
        assertNotEquals(user1.getRole(), user.getRole());
    }

    @DisplayName("유저삭제 요청성공")
    @Test
    void user_delete_true() throws Exception {

        //given 어떤 값이 주어지고
        User user = getUserContent();
        Long id = userService.save(user);
        String token = userService.createToken("her08072@naver.com");

        //when 무엇을 했을 때
        MockHttpServletRequestBuilder requestBuilder = post("/user/delete")
                .param("id", String.valueOf(id))
                .header("Authorization", "Bearer " + token);

        //then 어떤 값을 원한다.
        MvcResult result = this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    String getUser() {
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
        return userService.createToken("her08072@naver.com");
    }

    User getUserContent() {
        return User.builder()
                .name("홍길동")
                .email("her08072@naver.com")
                .password("1234")
                .userImageUrl("/img")
                .role(Role.ADMIN)
                .entranceYear("2016")
                .graduationYear("2022")
                .provider(Provider.KAKAO)
                .build();
    }
}