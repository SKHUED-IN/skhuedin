package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.comment.Comment;
import com.skhuedin.skhuedin.domain.user.Provider;
import com.skhuedin.skhuedin.domain.question.Question;
import com.skhuedin.skhuedin.domain.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql("/truncate.sql")
class CommentRepositoryTest {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    EntityManager em;

    Question question;
    User targetUser;
    User writerUser;

    @BeforeEach
    void beforeEach() {
        targetUser = User.builder()
                .email("target@email.com")
                .name("target")
                .userImageUrl("/img")
                .provider(Provider.SELF)
                .build();

        writerUser = User.builder()
                .email("writer@email.com")
                .name("writer")
                .userImageUrl("/img")
                .provider(Provider.SELF)
                .build();

        userRepository.save(targetUser);
        userRepository.save(writerUser);

        question = Question.builder()
                .targetUser(targetUser)
                .writerUser(writerUser)
                .title("질문 1")
                .content("질문1의 질문 내용")
                .build();

        questionRepository.save(question);
    }

    @Test
    @DisplayName("question 에 등록된 댓글 목록을 조회하는 테스트")
    void findByQuestionId() {

        // given
        Comment comment1 = generateComment(1);
        Comment comment2 = generateComment(2);

        commentRepository.save(comment1);
        commentRepository.save(comment2);

        // when
        List<Comment> comments = commentRepository.findByQuestionId(question.getId());

        // then
        assertEquals(comments.size(), 2);
    }

    @Test
    @DisplayName("findByQuestionId의 n + 1 문제를 확인하는 테스트")
    void findByQuestionId_N1() {

        // given
        for (int i = 0; i < 10; i++) {
            User user = generateUser(i);
            userRepository.save(user);
            Comment parent = generateComment(user, i);
            commentRepository.save(parent);
        }

        // when
        em.flush();
        em.clear();

        List<Comment> comments = commentRepository.findByQuestionId(question.getId());
        for (Comment comment : comments) {
            comment.getWriterUser().getEmail(); // 의도적으로 사용
        }

        // then
        assertEquals(comments.size(), 10);
    }

    Comment generateComment(int index) {
        return Comment.builder()
                .question(question)
                .writerUser(writerUser)
                .content("부모 댓글 " + index)
                .build();
    }

    Comment generateComment(User user, int index) {
        return Comment.builder()
                .question(question)
                .writerUser(user)
                .content("부모 댓글 " + index)
                .build();
    }

    User generateUser(int index) {
        return User.builder()
                .email("user" + index + "@email.com")
                .name("user" + index)
                .userImageUrl("/img")
                .provider(Provider.SELF)
                .build();
    }

    @AfterEach
    void afterEach() {
        commentRepository.deleteAll();
        questionRepository.deleteAll();
        userRepository.deleteAll();
    }
}