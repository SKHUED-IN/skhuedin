package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.Comment;
import com.skhuedin.skhuedin.domain.Question;
import com.skhuedin.skhuedin.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class CommentRepositoryTest {

    @Autowired CommentRepository commentRepository;
    @Autowired QuestionRepository questionRepository;
    @Autowired UserRepository userRepository;

    User targetUser;
    User writerUser;
    Question question;

    @BeforeEach
    void beforeEach() {
        targetUser = userRepository.findById(1L).get();
        writerUser = userRepository.findById(2L).get();

        question = Question.builder()
                .targetUser(targetUser)
                .writerUser(writerUser)
                .title("Spring")
                .content("spring 이 너무 어려워요.")
                .status(false)
                .fix(false)
                .build();

        question = questionRepository.save(question);
    }

    @Test
    @DisplayName("question id 별 comment list 를 확인하는 테스트")
    void findByQuestionId() {
        
        // given
        Long questionId = question.getId();
        Question question = questionRepository.findById(questionId).get();

        Comment comment1 = Comment.builder()
                .question(question)
                .content("댓글")
                .writerUser(writerUser)
                .build();

        Comment comment2 = Comment.builder()
                .question(question)
                .content("댓글")
                .writerUser(writerUser)
                .build();

        commentRepository.save(comment1);
        commentRepository.save(comment2);

        // when
        List<Comment> comments = commentRepository.findByQuestionId(questionId);

        // then
        assertAll(
                () -> assertEquals(comments.size(), 2),
                () -> assertEquals(comments.get(0).getQuestion(), comment1.getQuestion()),
                () -> assertEquals(comments.get(1).getQuestion(), comment2.getQuestion())
        );
    }
}