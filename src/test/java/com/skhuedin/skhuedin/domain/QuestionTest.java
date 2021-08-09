package com.skhuedin.skhuedin.domain;

import com.skhuedin.skhuedin.domain.question.Question;
import com.skhuedin.skhuedin.domain.user.Provider;
import com.skhuedin.skhuedin.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    private Question question;

    @BeforeEach
    void beforeEach() {
        User targetUser = User.builder()
                .email("target@email.com")
                .name("target")
                .userImageUrl("/img")
                .provider(Provider.SELF)
                .build();

        User writerUser = User.builder()
                .email("writer@email.com")
                .name("writer")
                .userImageUrl("/img")
                .provider(Provider.SELF)
                .build();

        question = Question.builder()
                .targetUser(targetUser)
                .writerUser(writerUser)
                .title("테스트 코드 관련 질문")
                .content("테스트 코드를 어떻게 작성하면 좋을까요?")
                .status(false)
                .fix(false)
                .build();
    }

    @Test
    @DisplayName("조회수를 3 증가 시키는 테스트")
    void addView() {

        // when
        question.addView();
        question.addView();
        question.addView();

        // then
        assertEquals(question.getView(), 3);
    }
}