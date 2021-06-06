package com.skhuedin.skhuedin.controller.admin.api;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @DisplayName("user 검색 없이 전체 조회")
    @Test
    void user_find_role() throws Exception {
        //given 어떤 값이 주어지고

        //when 무엇을 했을 때 then 어떤 값을 원한다.
        mockMvc.perform(get("/api/admin/users?page=0&size=10")
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
        //given 어떤 값이 주어지고

        //when 무엇을 했을 때 then 어떤 값을 원한다.
        mockMvc.perform(get("/api/admin/category?page=0&size=10")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

    }
}