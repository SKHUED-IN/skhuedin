package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.domain.Question;
import com.skhuedin.skhuedin.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionRepository questionRepository;

    User targetUser;
    User writerUser;

    @BeforeEach
    void beforeEach() {
        userRepository.deleteAll();
        questionRepository.deleteAll();

        targetUser = User.builder()
                .email("target@email.com")
                .password("1234")
                .name("target")
                .userImageUrl("/img")
                .graduationYear(LocalDateTime.now())
                .entranceYear(LocalDateTime.now())
                .provider(Provider.SELF)
                .build();

        writerUser = User.builder()
                .email("writer@email.com")
                .password("1234")
                .name("writer")
                .userImageUrl("/img")
                .graduationYear(LocalDateTime.now())
                .entranceYear(LocalDateTime.now())
                .provider(Provider.SELF)
                .build();

        userRepository.save(targetUser);
        userRepository.save(writerUser);
    }

    @Test
    @DisplayName("target user 의 id 별 question 목록을 수정날짜 내림차순으로 조회하는 테스트")
    void findByTargetId() {

        // given
        Question question1 = Question.builder()
                .targetUser(targetUser)
                .writerUser(writerUser)
                .title("질문 1")
                .content("질문1의 질문 내용")
                .status(false)
                .fix(false)
                .build();

        Question question2 = Question.builder()
                .targetUser(targetUser)
                .writerUser(writerUser)
                .title("질문 2")
                .content("질문2의 질문 내용")
                .status(false)
                .fix(false)
                .build();

        questionRepository.save(question1);
        questionRepository.save(question2);

        // when
        List<Question> questions = questionRepository.findByTargetUserIdOrderByLastModifiedDateDesc(targetUser.getId());

        // then
        assertAll(
                () -> assertEquals(questions.get(0).getLastModifiedDate()
                        .compareTo(questions.get(1).getLastModifiedDate()), 1)
        );
    }

    @AfterEach
    void afterEach() {
        userRepository.deleteAll();
        questionRepository.deleteAll();
    }
}