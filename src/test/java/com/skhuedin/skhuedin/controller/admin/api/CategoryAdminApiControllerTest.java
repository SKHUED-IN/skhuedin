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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
class CategoryAdminApiControllerTest {

    private MockMvc mockMvc;

    @Autowired
    UserService userService;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @DisplayName("Get으로 /categoryList 호출")
    @Test
    void categoryList_page() throws Exception {
        this.mockMvc.perform(get("/categoryList"))
                .andDo(print())
                .andExpect(view().name("contents/categoryList"))
                .andExpect(status().isOk());
    }

    @DisplayName("카테고리 리스트 요청성공")
    @Test
    void categoryList() throws Exception {

        //given 어떤 값이 주어지고
        User user = getUserContent();
        Long id = userService.save(user);
        String token = userService.createToken("her08072@naver.com");

        //when 무엇을 했을 때
        MockHttpServletRequestBuilder requestBuilder = post("/categoryList")
                .param("id", String.valueOf(id))
                .header("Authorization", "Bearer " + token);

        //then 어떤 값을 원한다.
        MvcResult result = this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
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