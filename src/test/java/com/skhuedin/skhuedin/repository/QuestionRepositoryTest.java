package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.Question;
import com.skhuedin.skhuedin.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class QuestionRepositoryTest {

    @Autowired UserRepository userRepository;
    @Autowired QuestionRepository questionRepository;

    User writerUser;
    User targetUser;

    @BeforeEach
    void beforeEach() {
        writerUser = userRepository.findById(1L).orElseThrow(() ->
                new NoSuchElementException("user 를 찾을 수 없습니다."));

        targetUser = userRepository.findById(2L).orElseThrow(() ->
                new NoSuchElementException("user 를 찾을 수 없습니다."));
    }
    
    @Test
    @DisplayName("question 를 생성하여 DB에 저장하는 테스트")
    void insert() {
        
        // given
        Question question = Question.builder()
                .title("java")
                .content("java 는 너무 어려워요")
                .targetUser(targetUser)
                .writerUser(writerUser)
                .build();

        // when
        Question saveQuestion = questionRepository.save(question);

        // then
        assertAll(() -> {
            assertEquals(question.getTitle(), saveQuestion.getTitle());
            assertEquals(question.getContent(), saveQuestion.getContent());
            assertEquals(question.getTargetUser(), saveQuestion.getTargetUser());
            assertEquals(question.getWriterUser(), saveQuestion.getWriterUser());
            assertEquals(question.getView(), saveQuestion.getView());
            assertEquals(question.getStatus(), saveQuestion.getStatus());
            assertEquals(question.getFix(), saveQuestion.getFix());
        });
    }

    @Test
    @DisplayName("저장한 question 을 단 건 조회하는 테스트")
    void findById() {

        // given
        Question question = Question.builder()
                .title("java")
                .content("java 는 너무 어려워요")
                .targetUser(targetUser)
                .writerUser(writerUser)
                .build();

        Question saveQuestion = questionRepository.save(question);

        // when
        Optional<Question> optionalQuestion = questionRepository.findById(saveQuestion.getId());
        Question findQuestion = optionalQuestion.orElseThrow(() ->
            new NoSuchElementException("question 을 찾지 못하였습니다.")
        );

        // then
        assertAll(() -> {
            assertEquals(saveQuestion.getTitle(), findQuestion.getTitle());
            assertEquals(saveQuestion.getContent(), findQuestion.getContent());
            assertEquals(saveQuestion.getTargetUser(), findQuestion.getTargetUser());
            assertEquals(saveQuestion.getWriterUser(), findQuestion.getWriterUser());
            assertEquals(saveQuestion.getView(), findQuestion.getView());
            assertEquals(saveQuestion.getStatus(), findQuestion.getStatus());
            assertEquals(saveQuestion.getFix(), findQuestion.getFix());
        });
    }

    @Test
    @DisplayName("저장한 question 을 모두 조회하는 테스트")
    void findAll() {
        
        // given
        Question question1 = Question.builder()
                .title("java")
                .content("java 는 너무 어려워요")
                .targetUser(targetUser)
                .writerUser(writerUser)
                .build();

        Question question2 = Question.builder()
                .title("spring")
                .content("spring 도 너무 어려워요")
                .targetUser(targetUser)
                .writerUser(writerUser)
                .build();

        // when
        questionRepository.save(question1);
        questionRepository.save(question2);

        // then
        assertEquals(questionRepository.findAll().size(), 2);
    }
}