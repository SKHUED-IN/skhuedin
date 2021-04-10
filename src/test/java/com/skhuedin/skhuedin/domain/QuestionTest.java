package com.skhuedin.skhuedin.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    User writerUser;
    User targetUser;

    @BeforeEach
    void beforeEach() {
        writerUser = User.builder()
                .email("writer@email.com")
                .name("홍길동")
                .password("1234")
                .provider(Provider.KAKAO)
                .entranceYear(LocalDateTime.of(2016, 3, 2, 0, 0))
                .graduationYear(LocalDateTime.of(2020, 2, 2, 0, 0))
                .userImageUrl("/img")
                .build();

        targetUser = User.builder()
                .email("target@email.com")
                .name("전우치")
                .password("1234")
                .provider(Provider.KAKAO)
                .entranceYear(LocalDateTime.of(2016, 3, 2, 0, 0))
                .graduationYear(LocalDateTime.of(2020, 2, 2, 0, 0))
                .userImageUrl("/img")
                .build();
    }

    @Test
    @DisplayName("Question 이 정상적으로 생성되었는지 확인하는 테스트")
    void createQuestion() {

        // given
        String title = "진로";
        String content = "진로를 찾게된 계기는 무엇인가요?";

        // when
        Question question = Question.builder()
                .title(title)
                .content(content)
                .writerUser(writerUser)
                .targetUser(targetUser)
                .build();

        // then
        assertAll(() -> {
            assertEquals(question.getTitle(), title);
            assertEquals(question.getContent(), content);
            assertEquals(question.getWriterUser(), writerUser);
            assertEquals(question.getTargetUser(), targetUser);
            assertEquals(question.getView(), 0);
            assertEquals(question.getStatus(), true);
            assertEquals(question.getFix(), true);
        });
    }

    @Test
    @DisplayName("question 의 수정을 확인하는 테스트")
    void updateQuestion() {

        // given
        Question question = Question.builder()
                .title("진로")
                .content("진로를 찾게된 계기는 무엇인가요?")
                .writerUser(writerUser)
                .targetUser(targetUser)
                .build();

        Question updateQuestion = Question.builder()
                .title("개발자")
                .content("좋은 개발자로 성장하는 방법은 무엇이 있을까요?")
                .writerUser(writerUser)
                .targetUser(targetUser)
                .build();

        // when
        question.updateQuestion(updateQuestion);

        // then
        assertAll(() -> {
            assertEquals(question.getTitle(), updateQuestion.getTitle());
            assertEquals(question.getContent(), updateQuestion.getContent());
            assertEquals(question.getWriterUser(), writerUser);
            assertEquals(question.getTargetUser(), targetUser);
            assertEquals(question.getView(), 0);
            assertEquals(question.getStatus(), true);
            assertEquals(question.getFix(), true);
        });
    }

    @Test
    @DisplayName("조회수 증가를 확인하는 테스트")
    void addView() {

        // given
        Question question = Question.builder()
                .title("진로")
                .content("진로를 찾게된 계기는 무엇인가요?")
                .writerUser(writerUser)
                .targetUser(targetUser)
                .build();

        // when
        // 조회수 3 증가
        question.addView();
        question.addView();
        question.addView();

        // then
        assertEquals(question.getView(), 3);
    }
}