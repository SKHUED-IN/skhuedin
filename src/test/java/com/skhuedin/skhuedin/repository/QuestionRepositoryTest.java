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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql("/truncate.sql")
class QuestionRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    EntityManager em;

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

    @Test
    @DisplayName("target user 의 id 별 question 목록을 paging 처리하여 조회하는 테스트")
    void findByTargetUserId_paging() {

        // given
        for (int i = 0; i < 30; i++) {
            Question question = generateQuestion(i);
            questionRepository.save(question);
        }

        // when
        PageRequest pageRequest = PageRequest.of(0, 5,
                Sort.by(Sort.Direction.DESC, "lastModifiedDate"));

        Page<Question> page = questionRepository.findByTargetUserId(targetUser.getId(), pageRequest);

        // then
        assertEquals(page.getContent().size(), 5); // 조회된 데이터 수
        assertEquals(page.getTotalElements(), 30); // 전체 데이터 수
        assertEquals(page.getNumber(), 0); // 페이지 번호
        assertEquals(page.getTotalPages(), 6); // 전체 페이지 번호
        assertTrue(page.isFirst()); // 첫 번째 페이지 t/f
        assertTrue(page.hasNext()); // 다음 페이지 t/f
    }

    @Test
    @DisplayName("findByTargetUserId의 N + 1 문제를 확인하는 테스트")
    void findByTargetId_N1() {

        // given
        for (int i = 0; i < 10; i++) {
            User writerUser = generateUser(i);
            userRepository.save(writerUser);
            Question question = generateQuestion(writerUser, i);
            questionRepository.save(question);
        }

        // when
        em.flush();
        em.clear();
        PageRequest pageRequest = PageRequest.of(0, 5,
                Sort.by(Sort.Direction.DESC, "lastModifiedDate"));

        Page<Question> page = questionRepository.findByTargetUserId(targetUser.getId(), pageRequest);
        for (Question question : page) {
            question.getWriterUser().getEmail(); // 의도적으로 사용
        }

        // then
        assertEquals(page.getContent().size(), 5); // 조회된 데이터 수
        assertEquals(page.getTotalElements(), 10); // 전체 데이터 수
        assertEquals(page.getNumber(), 0); // 페이지 번호
        assertEquals(page.getTotalPages(), 2); // 전체 페이지 번호
        assertTrue(page.isFirst()); // 첫 번째 페이지 t/f
        assertTrue(page.hasNext()); // 다음 페이지 t/f
    }

    private Question generateQuestion(int index) {
        return Question.builder()
                .targetUser(targetUser)
                .writerUser(writerUser)
                .title("질문 " + index)
                .content("질문의 질문 내용")
                .status(false)
                .fix(false)
                .build();
    }

    private Question generateQuestion(User writerUser, int index) {
        return Question.builder()
                .targetUser(targetUser)
                .writerUser(writerUser)
                .title("질문 " + index)
                .content("질문의 질문 내용")
                .status(false)
                .fix(false)
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
        questionRepository.deleteAll();
        userRepository.deleteAll();
    }
}