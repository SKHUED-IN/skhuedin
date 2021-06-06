package com.skhuedin.skhuedin.controller.admin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
class AdminMainControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @DisplayName("어드민 메인 페이 로딩 ")
    @Test
    void main_page_true() throws Exception {
        this.mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(view().name("admin/main"))
                .andExpect(status().isOk());
    }

    @DisplayName("게시글 관리 페이지 로딩 ")
    @Test
    void posts_page_true() throws Exception {
        this.mockMvc.perform(get("/admin/posts"))
                .andDo(print())
                .andExpect(view().name("admin/posts"))
                .andExpect(status().isOk());
    }

    @DisplayName("게시글 수정 페이지 로딩 ")
    @Test
    void posts_detail_page_true() throws Exception {
        this.mockMvc.perform(get("/admin/posts/detail"))
                .andDo(print())
                .andExpect(view().name("admin/posts-detail"))
                .andExpect(status().isOk());
    }

    @DisplayName("'회원 관리 페이지 로딩 ")
    @Test
    void user_page_true() throws Exception {
        this.mockMvc.perform(get("/admin/users"))
                .andDo(print())
                .andExpect(view().name("admin/admin-users"))
                .andExpect(status().isOk());
    }

    @DisplayName("'회원 수정 페이지 로딩 ")
    @Test
    void user_detail_page_true() throws Exception {
        this.mockMvc.perform(get("/admin/users/detail"))
                .andDo(print())
                .andExpect(view().name("admin/admin-users-detail"))
                .andExpect(status().isOk());
    }
}