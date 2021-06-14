package com.skhuedin.skhuedin.controller.admin.api;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@Sql("/truncate.sql")
@SpringBootTest
class AdminUserApiControllerTest {
    private MockMvc mockMvc;
//
//    @BeforeEach
//    void setUp(WebApplicationContext webApplicationContext) {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//                .build();
//    }
//
//    @DisplayName("user 검색 없이 전체 조회")
//    @Test
//    void user_find_role() throws Exception {
//        //given 어떤 값이 주어지고
//
//        //when 무엇을 했을 때 then 어떤 값을 원한다.
//        mockMvc.perform(get("/api/admin/users?page=0&size=10")
//                .param("role", "ADMIN")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andReturn();
//    }
//
//
//    @DisplayName("'/user'로 post요청")
//    @Test
//    void userList() throws Exception {
//
//        //given 어떤 값이 주어지고
//        String token = getUser();
//
//        //when 무엇을 했을 때
//        MockHttpServletRequestBuilder requestBuilder = post("/user")
//                .header("Authorization", "Bearer " + token);
//
//        //then 어떤 값을 원한다.
//        MvcResult result = this.mockMvc.perform(requestBuilder)
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$..['role']").exists())
//                .andReturn();
//
//
//        @DisplayName("category 전체 조회")
//    @Test
//    void category_find() throws Exception {
//        //given 어떤 값이 주어지고
//
//        //when 무엇을 했을 때 then 어떤 값을 원한다.
//        mockMvc.perform(get("/api/admin/category?page=0&size=10")
//                .param("role", "ADMIN")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andReturn();
//
//    }
}