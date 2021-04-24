package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.Comment;
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
class CommentRepositoryTest {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    Question question;
    User targetUser;
    User writerUser;

    @BeforeEach
    void beforeEach() {
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

        question = Question.builder()
                .targetUser(targetUser)
                .writerUser(writerUser)
                .title("질문 1")
                .content("질문1의 질문 내용")
                .status(false)
                .fix(false)
                .build();

        questionRepository.save(question);
    }

    @Test
    @DisplayName("question 에 등록된 부모 댓글 목록을 조회하는 테스트")
    void findByQuestionId() {

        // given
        Comment comment1 = Comment.builder()
                .question(question)
                .writerUser(writerUser)
                .content("부모 댓글1")
                .build();

        Comment comment2 = Comment.builder()
                .question(question)
                .writerUser(writerUser)
                .content("부모 댓글2")
                .build();

        Comment comment3 = Comment.builder()
                .question(question)
                .writerUser(writerUser)
                .content("부모 댓글1의 대댓글")
                .parent(comment1)
                .build();

        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);

        // when
        List<Comment> comments = commentRepository.findByQuestionId(question.getId());

        // then
        assertEquals(comments.size(), 2);
    }

    @Test
    @DisplayName("댓글 id 를 활용하여 대댓글을 조회하는 테스트")
    void findByParentId() {

        // given
        Comment parent = Comment.builder()
                .question(question)
                .writerUser(writerUser)
                .content("부모 댓글1")
                .build();

        Comment child1 = Comment.builder()
                .question(question)
                .writerUser(writerUser)
                .content("부모 댓글1의 대댓글")
                .parent(parent)
                .build();

        Comment child2 = Comment.builder()
                .question(question)
                .writerUser(writerUser)
                .content("부모 댓글2의 대댓글")
                .parent(parent)
                .build();

        commentRepository.save(parent);
        commentRepository.save(child1);
        commentRepository.save(child2);

        // when
        List<Comment> children = commentRepository.findByParentId(parent.getId());

        // then
        assertEquals(children.size(), 2);
    }

    @AfterEach
    void afterEach() {
        commentRepository.deleteAll();
        questionRepository.deleteAll();
        userRepository.deleteAll();
    }
}