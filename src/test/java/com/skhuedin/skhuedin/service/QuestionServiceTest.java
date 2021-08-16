package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.user.Provider;
import com.skhuedin.skhuedin.domain.user.Role;
import com.skhuedin.skhuedin.domain.user.User;
import com.skhuedin.skhuedin.dto.comment.CommentSaveRequestDto;
import com.skhuedin.skhuedin.dto.question.QuestionAdminMainResponseDto;
import com.skhuedin.skhuedin.dto.question.QuestionMainResponseDto;
import com.skhuedin.skhuedin.dto.question.QuestionSaveRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Sql("/truncate.sql")
class QuestionServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    static User generateUser(String email, String name) {
        return User.builder()
                .email(email)
                .name(name)
                .provider(Provider.SELF)
                .userImageUrl("/images/user.png")
                .role(Role.ROLE_USER)
                .build();
    }

    static QuestionSaveRequestDto generateQuestionSaveRequestDto(Long targetUserId, Long writerUserId) {
        return QuestionSaveRequestDto.builder()
                .targetUserId(targetUserId)
                .writerUserId(writerUserId)
                .title("테스트 question")
                .content("테스트 question content")
                .status(false)
                .fix(false)
                .build();
    }

    static CommentSaveRequestDto generateCommentSaveRequestDto(Long questionId, Long writerUserId) {
        return CommentSaveRequestDto.builder()
                .questionId(questionId)
                .writerUserId(writerUserId)
                .content("테스트 comment")
                .build();
    }

    @Test
    @DisplayName("QuestionSaveRequestDto를 활용하여 Question 객체를 생성하여 저장하는 테스트 - 성공")
    void saveNewQuestion() {

        // given
        User targetUser = generateUser("targetUser@email.com", "targetUser");
        User writerUser = generateUser("writerUser@email.com", "writerUser");

        userService.save(targetUser);
        userService.save(writerUser);

        QuestionSaveRequestDto requestDto = generateQuestionSaveRequestDto(targetUser.getId(), writerUser.getId());

        // when
        Long questionId = questionService.save(requestDto);
        QuestionMainResponseDto responseDto = questionService.findById(questionId);

        // then
        assertAll(
                () -> assertEquals(questionId, responseDto.getId()),
                () -> assertEquals(requestDto.getTargetUserId(), responseDto.getTargetUser().getId()),
                () -> assertEquals(requestDto.getWriterUserId(), responseDto.getWriterUser().getId()),
                () -> assertEquals(requestDto.getTitle(), responseDto.getTitle()),
                () -> assertEquals(requestDto.getContent(), responseDto.getContent())
        );
    }

    @Test
    @DisplayName("존재하지 않는 user로 인하여 예외를 던지는 테스트 - 실패")
    void saveWithNotExistUser() {

        // given
        QuestionSaveRequestDto requestDto = generateQuestionSaveRequestDto(0L, 0L);

        // when & then
        assertThrows(IllegalArgumentException.class,
                () -> questionService.save(requestDto)
        );
    }

    @Test
    @DisplayName("QuestionSaveRequestDto를 활용하여 update를 진행하는 테스트 - 성공")
    void update() {

        // given
        User targetUser = generateUser("targetUser@email.com", "targetUser");
        User writerUser = generateUser("writerUser@email.com", "writerUser");

        userService.save(targetUser);
        userService.save(writerUser);

        QuestionSaveRequestDto requestDto = generateQuestionSaveRequestDto(targetUser.getId(), writerUser.getId());
        Long questionId = questionService.save(requestDto);

        QuestionSaveRequestDto updateDto = QuestionSaveRequestDto.builder()
                .targetUserId(targetUser.getId())
                .writerUserId(writerUser.getId())
                .title("question 수정")
                .content("qustion 수정 content")
                .build();

        // when
        questionService.update(questionId, updateDto);
        QuestionMainResponseDto responseDto = questionService.findById(questionId);

        // then
        assertAll(
                () -> assertEquals(questionId, responseDto.getId()),
                () -> assertEquals(updateDto.getTargetUserId(), responseDto.getTargetUser().getId()),
                () -> assertEquals(updateDto.getWriterUserId(), responseDto.getWriterUser().getId()),
                () -> assertEquals(updateDto.getTitle(), responseDto.getTitle()),
                () -> assertEquals(updateDto.getContent(), responseDto.getContent())
        );
    }

    @Test
    @DisplayName("존재하지 않는 user로 인하여 예외를 던지는 테스트 - 실패")
    void updateWithNotExistUser() {

        // given
        User targetUser = generateUser("targetUser@email.com", "targetUser");
        User writerUser = generateUser("writerUser@email.com", "writerUser");

        userService.save(targetUser);
        userService.save(writerUser);

        QuestionSaveRequestDto requestDto = generateQuestionSaveRequestDto(targetUser.getId(), writerUser.getId());
        Long questionId = questionService.save(requestDto);

        QuestionSaveRequestDto updateDto = QuestionSaveRequestDto.builder()
                .targetUserId(0L)
                .writerUserId(0L)
                .title("question 수정")
                .content("qustion 수정 content")
                .build();

        // when & then
        assertThrows(IllegalArgumentException.class,
                () -> questionService.update(questionId, updateDto)
        );
    }

    @Test
    @DisplayName("존재하지 않는 question id로 예외를 던지는 테스트 - 실패")
    void updateWithNotQuestion() {

        // given
        User targetUser = generateUser("targetUser@email.com", "targetUser");
        User writerUser = generateUser("writerUser@email.com", "writerUser");

        userService.save(targetUser);
        userService.save(writerUser);

        QuestionSaveRequestDto updateDto = QuestionSaveRequestDto.builder()
                .targetUserId(0L)
                .writerUserId(0L)
                .title("question 수정")
                .content("qustion 수정 content")
                .build();

        // when & then
        assertThrows(IllegalArgumentException.class,
                () -> questionService.update(0L, updateDto)
        );
    }

    @Test
    @DisplayName("question을 정상적으로 삭제하는 테스트 - 성공")
    void delete() {

        // given
        User targetUser = generateUser("targetUser@email.com", "targetUser");
        User writerUser = generateUser("writerUser@email.com", "writerUser");

        userService.save(targetUser);
        userService.save(writerUser);

        QuestionSaveRequestDto requestDto = generateQuestionSaveRequestDto(targetUser.getId(), writerUser.getId());
        Long questionId = questionService.save(requestDto);

        // when
        questionService.delete(questionId);

        // then
        assertThrows(IllegalArgumentException.class,
                () -> userService.findById(questionId)
        );
    }

    @Test
    @DisplayName("존재하지 않는 question을 삭제하는 테스트 - 실패")
    void deleteWithNotExistQuestion() {

        // given & when & then
        assertThrows(IllegalArgumentException.class,
                () -> userService.findById(0L)
        );
    }

    @Test
    @DisplayName("question id를 활용하여 조회하는 테스틑 - 성공")
    void findById() {

        // given
        User targetUser = generateUser("targetUser@email.com", "targetUser");
        User writerUser = generateUser("writerUser@email.com", "writerUser");

        userService.save(targetUser);
        userService.save(writerUser);

        QuestionSaveRequestDto requestDto = generateQuestionSaveRequestDto(targetUser.getId(), writerUser.getId());
        Long questionId = questionService.save(requestDto);

        for (int i = 0; i < 10; i++) {
            CommentSaveRequestDto commentSaveRequestDto = generateCommentSaveRequestDto(questionId, writerUser.getId());
            commentService.save(commentSaveRequestDto);
        }

        // when
        QuestionMainResponseDto responseDto = questionService.findById(questionId);

        // then
        assertAll(
                () -> assertEquals(questionId, responseDto.getId()),
                () -> assertEquals(requestDto.getTargetUserId(), responseDto.getTargetUser().getId()),
                () -> assertEquals(requestDto.getWriterUserId(), responseDto.getWriterUser().getId()),
                () -> assertEquals(requestDto.getTitle(), responseDto.getTitle()),
                () -> assertEquals(requestDto.getContent(), responseDto.getContent()),
                () -> assertEquals(10, responseDto.getComments().size())
        );
    }

    @Test
    @DisplayName("존재하지 않는 question id로 조회하여 예외를 던지는 테스트 - 실패")
    void findByIdWithNotExistQuestion() {

        // given & when & then
        assertThrows(IllegalArgumentException.class,
                () -> questionService.findById(0L)
        );
    }

    @Test
    @DisplayName("targetUser id를 활용하여 question 목록을 paging하여 조회하는 테스트 - 성공")
    void findByTargetUserIdWithPaging() {

        // given
        User targetUser = generateUser("targetUser@email.com", "targetUser");
        User writerUser = generateUser("writerUser@email.com", "writerUser");

        userService.save(targetUser);
        userService.save(writerUser);

        for (int i = 0; i < 30; i++) {
            QuestionSaveRequestDto requestDto = generateQuestionSaveRequestDto(targetUser.getId(), writerUser.getId());
            questionService.save(requestDto);
        }

        // when
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<QuestionMainResponseDto> page = questionService.findByTargetUserId(targetUser.getId(), pageRequest);

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
    @DisplayName("조회수를 3 증가 시키는 테스트 - 성공")
    void addView() {

        // given
        User targetUser = generateUser("targetUser@email.com", "targetUser");
        User writerUser = generateUser("writerUser@email.com", "writerUser");

        userService.save(targetUser);
        userService.save(writerUser);

        QuestionSaveRequestDto requestDto = generateQuestionSaveRequestDto(targetUser.getId(), writerUser.getId());
        Long questionId = questionService.save(requestDto);

        // when
        questionService.addView(questionId);
        questionService.addView(questionId);
        questionService.addView(questionId);

        // then
        QuestionMainResponseDto responseDto = questionService.findById(questionId);
        assertEquals(3, responseDto.getView());
    }

    @Test
    @DisplayName("question 목록을 paging하여 조회하는 테스트 - 성공")
    void findByAllWithPaging() {

        // given
        User targetUser = generateUser("targetUser@email.com", "targetUser");
        User writerUser = generateUser("writerUser@email.com", "writerUser");

        userService.save(targetUser);
        userService.save(writerUser);

        for (int i = 0; i < 30; i++) {
            QuestionSaveRequestDto requestDto = generateQuestionSaveRequestDto(targetUser.getId(), writerUser.getId());
            questionService.save(requestDto);
        }

        // when
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<QuestionAdminMainResponseDto> page = questionService.findAll(pageRequest);

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

        userService.save(targetUser);
        userService.save(writerUser);

        for (int i = 0; i < 30; i++) {
            QuestionSaveRequestDto requestDto = generateQuestionSaveRequestDto(targetUser.getId(), writerUser.getId());
            questionService.save(requestDto);
        }

        // when
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<QuestionAdminMainResponseDto> page = questionService.findByWriterUserName(pageRequest, writerUser.getName());

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

        userService.save(targetUser);
        userService.save(writerUser);

        for (int i = 0; i < 30; i++) {
            QuestionSaveRequestDto requestDto = generateQuestionSaveRequestDto(targetUser.getId(), writerUser.getId());
            questionService.save(requestDto);
        }

        // when
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<QuestionAdminMainResponseDto> page = questionService.findByTargetUserName(pageRequest, targetUser.getName());

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