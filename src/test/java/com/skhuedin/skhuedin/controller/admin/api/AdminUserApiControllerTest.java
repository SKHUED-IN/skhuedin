package com.skhuedin.skhuedin.controller.admin.api;

import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.infra.JwtTokenProvider;
import com.skhuedin.skhuedin.infra.Role;
import com.skhuedin.skhuedin.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("/truncate.sql")
@SpringBootTest
class AdminUserApiControllerTest {
    private MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    String token;

    AdminUserApiControllerTest() throws Exception {
    }

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();

        User user = User.builder()
                .name("admin")
                .email("test@naver.com")
                .password("1234")
                .role(Role.ADMIN)
                .userImageUrl("/img")
                .entranceYear("2016")
                .graduationYear("2022")
                .provider(Provider.KAKAO)
                .build();
        userService.save(user);

        token = jwtTokenProvider.createToken("test@naver.com");
    }

    @DisplayName("user 검색 없이 전체 조회")
    @Test
    void user_find_role() throws Exception {

        //given & when
        mockMvc.perform(get("/api/admin/users?page=0&size=10")
                .header("Authorization", "Bearer " + token)
                .param("role", "ADMIN")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @DisplayName("category 전체 조회")
    @Test
    void category_find() throws Exception {

        mockMvc.perform(get("/api/admin/category?page=0&size=10")
                .header("Authorization", "Bearer " + token)
                .param("role", "ADMIN")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }
}