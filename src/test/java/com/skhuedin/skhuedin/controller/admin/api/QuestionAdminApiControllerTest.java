package com.skhuedin.skhuedin.controller.admin.api;

import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.domain.Question;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.dto.question.QuestionSaveRequestDto;
import com.skhuedin.skhuedin.infra.Role;
import com.skhuedin.skhuedin.repository.QuestionRepository;
import com.skhuedin.skhuedin.service.QuestionService;
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
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Sql("/truncate.sql")
@SpringBootTest
class QuestionAdminApiControllerTest {
    private MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    @Autowired
    QuestionRepository questionRepository;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @DisplayName("'/question'로 get요청")
    @Test
    void postList_page_true() throws Exception {
        this.mockMvc.perform(get("/question"))
                .andDo(print())
                .andExpect(view().name("contents/questionList"))
                .andExpect(status().isOk());
    }

    @DisplayName("'/question'로 post요청")
    @Test
    void questionList() throws Exception {

        //given 어떤 값이 주어지고
        String token = getUser();

        //when 무엇을 했을 때
        MockHttpServletRequestBuilder requestBuilder = post("/question")
                .header("Authorization", "Bearer " + token);

        //then 어떤 값을 원한다.
        MvcResult result = this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @DisplayName("'status 변경")
    @Test
    void question_status_update() throws Exception {

        //given 어떤 값이 주어지고
        String token = getUser();
        Long saveId = questionService.save(generateRequestDto());

        //when 무엇을 했을 때
        MockHttpServletRequestBuilder requestBuilder = post("/question/status")
                .param("id", String.valueOf(saveId))
                .header("Authorization", "Bearer " + token);

        //then 어떤 값을 원한다.
        MvcResult result = this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Question findQuestion = questionRepository.findById(saveId).orElseThrow(() ->
                new IllegalArgumentException("해당 question 이 존재하지 않습니다. id="));

        assertNotEquals(false, findQuestion.getStatus());
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

    QuestionSaveRequestDto generateRequestDto() {

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

        User user2 = User.builder()
                .name("홍길동")
                .email("her08072@naver.com")
                .password("1234")
                .userImageUrl("/img")
                .role(Role.ADMIN)
                .entranceYear("2016")
                .graduationYear("2022")
                .provider(Provider.KAKAO)
                .build();
        Long id2 = userService.save(user);

        return QuestionSaveRequestDto.builder()
                .targetUserId(id)
                .writerUserId(id2)
                .title("Spring")
                .content("Spring 은 어떤식으로 공부하는 것이 좋을까요?")
                .status(false)
                .fix(false)
                .build();
    }
}