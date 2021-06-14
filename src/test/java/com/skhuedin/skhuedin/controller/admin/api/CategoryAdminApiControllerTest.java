package com.skhuedin.skhuedin.controller.admin.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skhuedin.skhuedin.dto.category.CategoryRequestDto;
import com.skhuedin.skhuedin.service.CategoryService;
import com.skhuedin.skhuedin.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("/truncate.sql")
@SpringBootTest
class CategoryAdminApiControllerTest {

    private MockMvc mockMvc;
    private CategoryRequestDto category;
    private int saveId;

    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();

        category = CategoryRequestDto.builder()
                .name("test1")
                .weight(4L)
                .build();
        saveId = Math.toIntExact(categoryService.save(category));

        category = CategoryRequestDto.builder()
                .name("test2")
                .weight(4L)
                .build();
        categoryService.save(category);
    }

    @DisplayName("category findAll")
    @Test
    void categories() throws Exception {

        this.mockMvc.perform(get("/api/admin/categories"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$..data[0].name").value("test1"))
                .andExpect(jsonPath("$..data[1].name").value("test2"));
    }

    @DisplayName("카테고리 전체 조회 cmd 없이 요청(id 기준)")
    @Test
    void categoryList_paging_sortById_true() throws Exception {

        //given & when
        MockHttpServletRequestBuilder requestBuilder = get("/api/admin/category")
                .params(getPage());

        //then 어떤 값을 원한다.
        MvcResult result = this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @DisplayName("카테고리 전체 조회 가중치 정렬 기준으로 요청")
    @Test
    void categoryList_paging_sortByWeight_true() throws Exception {

        //given & when
        MockHttpServletRequestBuilder requestBuilder = get("/api/admin/category")
                .param("cmd", String.valueOf(1))
                .params(getPage());

        //then 어떤 값을 원한다.
        MvcResult result = this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..content[0].weight").value(4))
                .andReturn();
    }

    @DisplayName("카테고리 전체 조회 생성일 정렬 기준으로 요청")
    @Test
    void categoryList_paging_sortByCreateDate_true() throws Exception {

        //given & when
        MockHttpServletRequestBuilder requestBuilder = get("/api/admin/category")
                .param("cmd", String.valueOf(2))
                .params(getPage());

        //then 어떤 값을 원한다.
        MvcResult result = this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @DisplayName("카테고리 단건 조회")
    @Test
    void findById_true() throws Exception {

        //given & when & then
        MvcResult result = this.mockMvc.perform(get("/api/admin/category/" + saveId))
                .andDo(print())
                .andExpect(jsonPath("$..id").value(saveId))
                .andExpect(status().isOk())
                .andReturn();
    }

    @DisplayName("카테고리 가중치 증가")
    @Test
    void weight_up_true() throws Exception {
        //given
        int weight = Math.toIntExact(category.getWeight()) + 1;

        //when
        MvcResult result = this.mockMvc.perform(get("/api/admin/category/up/" + saveId))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
        //then
        MvcResult result2 = this.mockMvc.perform(get("/api/admin/category/" + saveId))
                .andDo(print())
                .andExpect(jsonPath("$..weight").value(weight))
                .andReturn();
    }

    @DisplayName("카테고리 가중치 감소")
    @Test
    void weight_down_true() throws Exception {
        //given
        int weight = Math.toIntExact(category.getWeight()) - 1;

        //when
        MvcResult result = this.mockMvc.perform(get("/api/admin/category/down/" + saveId))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
        //then
        MvcResult result2 = this.mockMvc.perform(get("/api/admin/category/" + saveId))
                .andDo(print())
                .andExpect(jsonPath("$..weight").value(weight))
                .andReturn();
    }

    @DisplayName("카테고리 생성 성공")
    @Test
    void category_create_true() throws Exception {
        //given
        String content = objectMapper.writeValueAsString(new CategoryRequestDto("테스트", 4L));

        //when & then
        this.mockMvc.perform(post("/api/admin/category")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @DisplayName("카테고리 삭제")
    @Test
    void category_delete_true() throws Exception {
        //given
        this.mockMvc.perform(delete("/api/admin/category/" + saveId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    MultiValueMap<String, String> getPage() {

        MultiValueMap<String, String> requestParam = new LinkedMultiValueMap<>();
        requestParam.add("page", "0");
        requestParam.add("size", "10");

        return requestParam;
    }
}