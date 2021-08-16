package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.question.Question;
import com.skhuedin.skhuedin.domain.user.Provider;
import com.skhuedin.skhuedin.domain.user.Role;
import com.skhuedin.skhuedin.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql("/truncate.sql")
class QuestionRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionRepository questionRepository;

    static User generateUser(String email, String name) {
        return User.builder()
                .email(email)
                .name(name)
                .provider(Provider.SELF)
                .userImageUrl("/images/user.png")
                .role(Role.ROLE_USER)
                .build();
    }

    static Question generateQuestion(User targetUser, User writerUSer) {
        return Question.builder()
                .targetUser(targetUser)
                .writerUser(writerUSer)
                .title("테스트 question")
                .content("테스트 question content")
                .build();
    }

    @Test
    @DisplayName("targetUser id별 question 목록을 paging 처리하여 조회하는 테스트 - 성공")
    void findByTargetUserIdWithPaging() {

        // given
        User targetUser = generateUser("targetUser@email.com", "targetUser");
        User writerUser = generateUser("writerUser@email.com", "writerUser");

        userRepository.save(targetUser);
        userRepository.save(writerUser);

        for (int i = 0; i < 30; i++) {
            Question question = generateQuestion(targetUser, writerUser);
            questionRepository.save(question);
        }

        // when
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<Question> page = questionRepository.findByTargetUserId(targetUser.getId(), pageRequest);

        // then
        assertAll(
                () -> assertEquals(page.getContent().size(), 5), // 조회된 데이터 수
                () -> assertEquals(page.getTotalElements(), 30), // 전체 데이터 수
                () -> assertEquals(page.getNumber(), 0), // 페이지 번호
                () -> assertEquals(page.getTotalPages(), 6), // 전체 페이지 번호
                () -> assertTrue(page.isFirst()), // 첫 번째 페이지 t/f
                () -> assertTrue(page.hasNext()) // 다음 페이지 t/f
        );
    }

    @Test
    @DisplayName("writerUser id별 question 목록을 전체 조회하는 테스트 - 성공")
    void findByWriterUserId() {

        // given
        User targetUser = generateUser("targetUser@email.com", "targetUser");
        User writerUser = generateUser("writerUser@email.com", "writerUser");

        userRepository.save(targetUser);
        userRepository.save(writerUser);

        for (int i = 0; i < 30; i++) {
            Question question = generateQuestion(targetUser, writerUser);
            questionRepository.save(question);
        }

        // when
        List<Question> questions = questionRepository.findByWriterUserId(writerUser.getId());

        // then
        assertEquals(30, questions.size());
    }

    @Test
    @DisplayName("targetUser id별 question 목록을 전체 조회하는 테스트 - 성공")
    void findByTargetUserId() {

        // given
        User targetUser = generateUser("targetUser@email.com", "targetUser");
        User writerUser = generateUser("writerUser@email.com", "writerUser");

        userRepository.save(targetUser);
        userRepository.save(writerUser);

        for (int i = 0; i < 30; i++) {
            Question question = generateQuestion(targetUser, writerUser);
            questionRepository.save(question);
        }

        // when
        List<Question> questions = questionRepository.findByTargetUserId(targetUser.getId());

        // then
        assertEquals(30, questions.size());
    }

    @Test
    @DisplayName("question 전체 목록을 paging하여 조회하는 테스트 - 성공")
    void findAllWithPaging() {

        // given
        User targetUser = generateUser("targetUser@email.com", "targetUser");
        User writerUser = generateUser("writerUser@email.com", "writerUser");

        userRepository.save(targetUser);
        userRepository.save(writerUser);

        for (int i = 0; i < 30; i++) {
            Question question = generateQuestion(targetUser, writerUser);
            questionRepository.save(question);
        }

        // when
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<Question> page = questionRepository.findAll(pageRequest);

        // then
        assertAll(
                () -> assertEquals(5, page.getContent().size()), // 조회된 데이터 수
                () -> assertEquals(30, page.getTotalElements()), // 전체 데이터 수
                () -> assertEquals(0, page.getNumber()), // 페이지 번호
                () -> assertEquals(6, page.getTotalPages()), // 전체 페이지 번호
                () -> assertTrue(page.isFirst()), // 첫 번째 페이지 t/f
                () -> assertTrue(page.hasNext()) // 다음 페이지 t/f
        );
    }

    @Test
    @DisplayName("writerUser의 name별 question 목록을 paging하여 조회하는 테스트 - 성공")
    void findByWriterUserNameWithPaging() {

        // given
        User targetUser = generateUser("targetUser@email.com", "targetUser");
        User writerUser = generateUser("writerUser@email.com", "writerUser");

        userRepository.save(targetUser);
        userRepository.save(writerUser);

        for (int i = 0; i < 30; i++) {
            Question question = generateQuestion(targetUser, writerUser);
            questionRepository.save(question);
        }

        // when
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<Question> page = questionRepository.findByWriterUserName(pageRequest, writerUser.getName());

        // then
        assertAll(
                () -> assertEquals(5, page.getContent().size()), // 조회된 데이터 수
                () -> assertEquals(30, page.getTotalElements()), // 전체 데이터 수
                () -> assertEquals(0, page.getNumber()), // 페이지 번호
                () -> assertEquals(6, page.getTotalPages()), // 전체 페이지 번호
                () -> assertTrue(page.isFirst()), // 첫 번째 페이지 t/f
                () -> assertTrue(page.hasNext()) // 다음 페이지 t/f
        );
    }

    @Test
    @DisplayName("targetUser의 name별 question 목록을 paging하여 조회하는 테스트 - 성공")
    void findByTargetUserNameWithPaging() {

        // given
        User targetUser = generateUser("targetUser@email.com", "targetUser");
        User writerUser = generateUser("writerUser@email.com", "writerUser");

        userRepository.save(targetUser);
        userRepository.save(writerUser);

        for (int i = 0; i < 30; i++) {
            Question question = generateQuestion(targetUser, writerUser);
            questionRepository.save(question);
        }

        // when
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<Question> page = questionRepository.findByTargetUserName(pageRequest, targetUser.getName());

        // then
        assertAll(
                () -> assertEquals(5, page.getContent().size()), // 조회된 데이터 수
                () -> assertEquals(30, page.getTotalElements()), // 전체 데이터 수
                () -> assertEquals(0, page.getNumber()), // 페이지 번호
                () -> assertEquals(6, page.getTotalPages()), // 전체 페이지 번호
                () -> assertTrue(page.isFirst()), // 첫 번째 페이지 t/f
                () -> assertTrue(page.hasNext()) // 다음 페이지 t/f
        );
    }
}