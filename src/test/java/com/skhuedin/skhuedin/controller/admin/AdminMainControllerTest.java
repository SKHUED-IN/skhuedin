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

    @DisplayName("어드민 메인 페이 로딩")
    @Test
    void main_page_true() throws Exception {
        this.mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(view().name("admin/main"))
                .andExpect(status().isOk());
    }

    @DisplayName("게시글 관리 페이지 로딩")
    @Test
    void posts_page_true() throws Exception {
        this.mockMvc.perform(get("/admin/posts"))
                .andDo(print())
                .andExpect(view().name("admin/posts/posts"))
                .andExpect(status().isOk());
    }

    @DisplayName("게시글 수정 페이지 로딩 ")
    @Test
    void posts_detail_page_true() throws Exception {
        this.mockMvc.perform(get("/admin/posts/detail"))
                .andDo(print())
                .andExpect(view().name("admin/posts/posts-detail"))
                .andExpect(status().isOk());
    }

    @DisplayName("질문 관리 페이지 로딩")
    @Test
    void question_page_true() throws Exception {
        this.mockMvc.perform(get("/admin/question"))
                .andDo(print())
                .andExpect(view().name("admin/question/question"))
                .andExpect(status().isOk());
    }

    @DisplayName("질문 관리 수정 페이지 로딩")
    @Test
    void question_detail_page_true() throws Exception {
        this.mockMvc.perform(get("/admin/question/detail"))
                .andDo(print())
                .andExpect(view().name("admin/question/question-detail"))
                .andExpect(status().isOk());
    }

    @DisplayName("회원 관리 페이지 로딩")
    @Test
    void user_page_true() throws Exception {
        this.mockMvc.perform(get("/admin/users"))
                .andDo(print())
                .andExpect(view().name("admin/user/users"))
                .andExpect(status().isOk());
    }

    @DisplayName("회원 수정 페이지 로딩")
    @Test
    void user_detail_page_true() throws Exception {
        this.mockMvc.perform(get("/admin/users/detail"))
                .andDo(print())
                .andExpect(view().name("admin/user/users-detail"))
                .andExpect(status().isOk());
    }

    @DisplayName("카테고리 관리 페이지 로딩")
    @Test
    void category_page_true() throws Exception {
        this.mockMvc.perform(get("/admin/category"))
                .andDo(print())
                .andExpect(view().name("admin/category/category"))
                .andExpect(status().isOk());
    }

    @DisplayName("카테고리 관리 수정 페이지 로딩")
    @Test
    void category_detail_page_true() throws Exception {
        this.mockMvc.perform(get("/admin/category/detail"))
                .andDo(print())
                .andExpect(view().name("admin/category/category-detail"))
                .andExpect(status().isOk());
    }
}