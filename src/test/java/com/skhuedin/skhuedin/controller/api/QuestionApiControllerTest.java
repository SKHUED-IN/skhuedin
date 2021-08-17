package com.skhuedin.skhuedin.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skhuedin.skhuedin.domain.user.Provider;
import com.skhuedin.skhuedin.domain.user.Role;
import com.skhuedin.skhuedin.domain.user.User;
import com.skhuedin.skhuedin.dto.question.QuestionMainResponseDto;
import com.skhuedin.skhuedin.dto.question.QuestionSaveRequestDto;
import com.skhuedin.skhuedin.service.CommentService;
import com.skhuedin.skhuedin.service.QuestionService;
import com.skhuedin.skhuedin.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Sql("/truncate.sql")
class QuestionApiControllerTest {

    MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 필터 추가
                .build();
    }

    static User generateUser(String email, String name) {
        return User.builder()
                .email(email)
                .name(name)
                .provider(Provider.SELF)
                .userImageUrl("/images/user.png")
                .role(Role.ROLE_USER)
                .build();
    }

    static QuestionSaveRequestDto generateQuestionSaveRequestDto(Long targetUserId, Long writerUserId) {
        return QuestionSaveRequestDto.builder()
                .targetUserId(targetUserId)
                .writerUserId(writerUserId)
                .title("테스트 question")
                .content("테스트 question content")
                .status(false)
                .fix(false)
                .build();
    }

    @Test
    @DisplayName("POST /api/questions - 성공")
    @WithMockUser(username = "writerUser@email.com")
    void saveQuestion() throws Exception {

        // given
        User targetUser = generateUser("targetUser@email.com", "targetUser");
        User writerUser = generateUser("writerUser@email.com", "writerUser");

        Long targetUserId = userService.save(targetUser);
        Long writerUserId = userService.save(writerUser);

        // when
        QuestionSaveRequestDto requestDto = generateQuestionSaveRequestDto(targetUserId, writerUserId);

        // then
        mockMvc.perform(post("/api/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("GET /api/questions/{questionId} - 성공")
    void getQuestion() throws Exception {

        // given
        User targetUser = generateUser("targetUser@email.com", "targetUser");
        User writerUser = generateUser("writerUser@email.com", "writerUser");

        Long targetUserId = userService.save(targetUser);
        Long writerUserId = userService.save(writerUser);

        QuestionSaveRequestDto requestDto = generateQuestionSaveRequestDto(targetUserId, writerUserId);

        // when
        Long questionId = questionService.save(requestDto);
        QuestionMainResponseDto responseDto = questionService.findById(questionId);

        // then
        mockMvc.perform(get("/api/questions/{questionId}", responseDto.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/users/{targetUserId}/questions - 성공")
    void findByTargetIdWithPaging() throws Exception {

        // given
        User targetUser = generateUser("targetUser@email.com", "targetUser");
        User writerUser = generateUser("writerUser@email.com", "writerUser");

        userService.save(targetUser);
        userService.save(writerUser);

        for (int i = 0; i < 30; i++) {
            QuestionSaveRequestDto requestDto = generateQuestionSaveRequestDto(targetUser.getId(), writerUser.getId());
            questionService.save(requestDto);
        }

        // when
        PageRequest pageRequest = PageRequest.of(0, 5);

        // then
        mockMvc.perform(get("/api/users/{targetUserId}/questions", targetUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pageRequest)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PUT /api/questions/{questionId} - 성공")
    @WithMockUser(username = "writerUser@email.com")
    void updateQuestion() throws Exception {

        // given
        User targetUser = generateUser("targetUser@email.com", "targetUser");
        User writerUser = generateUser("writerUser@email.com", "writerUser");

        userService.save(targetUser);
        userService.save(writerUser);

        QuestionSaveRequestDto requestDto = generateQuestionSaveRequestDto(targetUser.getId(), writerUser.getId());
        Long questionId = questionService.save(requestDto);

        // when
        QuestionSaveRequestDto updateDto = QuestionSaveRequestDto.builder()
                .targetUserId(targetUser.getId())
                .writerUserId(writerUser.getId())
                .title("question 수정")
                .content("qustion 수정 content")
                .status(false)
                .fix(false)
                .build();

        // then
        mockMvc.perform(put("/api/questions/{questionId}", questionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /api/questions/{questionId} - 성공")
    @WithMockUser(username = "writerUser@email.com")
    void deleteQuestion() throws Exception {

        // given
        User targetUser = generateUser("targetUser@email.com", "targetUser");
        User writerUser = generateUser("writerUser@email.com", "writerUser");

        userService.save(targetUser);
        userService.save(writerUser);

        QuestionSaveRequestDto requestDto = generateQuestionSaveRequestDto(targetUser.getId(), writerUser.getId());

        // when
        Long questionId = questionService.save(requestDto);

        // then
        mockMvc.perform(delete("/api/questions/{questionId}", questionId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


}