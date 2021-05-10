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
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
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
        Comment comment1 = generateParent(1);
        Comment comment2 = generateParent(2);
        Comment comment3 = generateChild(comment1, 1);

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
        Comment parent = generateParent(1);
        Comment child1 = generateChild(parent, 1);
        Comment child2 = generateChild(parent, 2);

        commentRepository.save(parent);
        commentRepository.save(child1);
        commentRepository.save(child2);

        // when
        List<Comment> children = commentRepository.findByParentId(parent.getId());

        // then
        assertEquals(children.size(), 2);
    }

    @Test
    @DisplayName("findByQuestionId의 n + 1 문제를 확인하는 테스트")
    void findByQuestionId_N1() {

        // given
        for (int i = 0; i < 10; i++) {
            User user = generateUser(i);
            userRepository.save(user);
            Comment parent = generateParent(user, i);
            commentRepository.save(parent);
        }

        // when
        em.flush();
        em.clear();
        System.out.println("==================== 영속성 컨텍스트 초기화 ====================");

        List<Comment> comments = commentRepository.findByQuestionId(question.getId());
        for (Comment comment : comments) {
            comment.getWriterUser().getEmail(); // 의도적으로 사용
        }

        // then
        assertEquals(comments.size(), 10);
    }

    @Test
    @DisplayName("findByParentId의 n + 1 문제를 확인하는 테스트")
    void findByParentId_N1() {

        // given
        Comment parent = generateParent(1);
        commentRepository.save(parent);

        for (int i = 0; i < 10; i++) {
            User user = generateUser(i);
            userRepository.save(user);
            Comment comment = generateChild(user, parent, i);
            commentRepository.save(comment);
        }

        // when
        em.flush();
        em.clear();
        System.out.println("==================== 영속성 컨텍스트 초기화 ====================");

        List<Comment> comments = commentRepository.findByParentId(parent.getId());
        for (Comment comment : comments) {
            comment.getWriterUser().getEmail();
        }

        // then
        assertEquals(comments.size(), 10);
    }

    Comment generateParent(int index) {
        return Comment.builder()
                .question(question)
                .writerUser(writerUser)
                .content("부모 댓글 " + index)
                .build();
    }

    Comment generateParent(User user, int index) {
        return Comment.builder()
                .question(question)
                .writerUser(user)
                .content("부모 댓글 " + index)
                .build();
    }

    Comment generateChild(Comment parent, int index) {
        return Comment.builder()
                .question(question)
                .writerUser(writerUser)
                .content("부모 댓글 " + index)
                .parent(parent)
                .build();
    }

    Comment generateChild(User user, Comment parent, int index) {
        return Comment.builder()
                .question(question)
                .writerUser(user)
                .content("부모 댓글 " + index)
                .parent(parent)
                .build();
    }

    User generateUser(int index) {
        return User.builder()
                .email("user" + index + "@email.com")
                .password("1234")
                .name("user" + index)
                .userImageUrl("/img")
                .graduationYear(LocalDateTime.now())
                .entranceYear(LocalDateTime.now())
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