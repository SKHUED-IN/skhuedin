package com.skhuedin.skhuedin.domain;

import com.skhuedin.skhuedin.domain.question.Question;
import com.skhuedin.skhuedin.domain.user.Provider;
import com.skhuedin.skhuedin.domain.user.Role;
import com.skhuedin.skhuedin.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class QuestionTest {

    static User generateUser(String email, String name) {
        return User.builder()
                .email(email)
                .name(name)
                .provider(Provider.SELF)
                .userImageUrl("/images/user.png")
                .role(Role.ROLE_USER)
                .build();
    }

    @Test
    @DisplayName("Builder를 활용하여 Question 객체를 생성하는 테스트 - 성공")
    void createByBuilder() {

        // given
        User targetUser = generateUser("targetUser@email.com", "targetUser");
        User writerUser = generateUser("writerUser@email.com", "writerUser");

        // when
        Question question = Question.builder()
                .targetUser(targetUser)
                .writerUser(writerUser)
                .title("테스트 question")
                .content("테스트 question content")
                .build();

        // then
        assertAll(
                () -> assertEquals(targetUser, question.getTargetUser()),
                () -> assertEquals(writerUser, question.getWriterUser())
        );
    }

    @Test
    @DisplayName("조회수를 2 증가 시키는 테스트 - 성공")
    void addView() {

        // given
        User targetUser = generateUser("targetUser@email.com", "targetUser");
        User writerUser = generateUser("writerUser@email.com", "writerUser");

        Question question = Question.builder()
                .targetUser(targetUser)
                .writerUser(writerUser)
                .title("테스트 question")
                .content("테스트 question content")
                .build();

        // when
        question.addView();
        question.addView();

        // then
        assertEquals(2, question.getView());
    }
}