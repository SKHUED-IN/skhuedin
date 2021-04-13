package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.Comment;
import com.skhuedin.skhuedin.domain.Question;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.dto.comment.CommentMainResponseDto;
import com.skhuedin.skhuedin.dto.comment.CommentSaveRequestDto;
import com.skhuedin.skhuedin.dto.question.QuestionSaveRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired CommentService commentService;
    @Autowired QuestionService questionService;
    @Autowired UserService userService;

    User targetUser;
    User writerUser;
    Question question;

    @BeforeEach
    void beforeEach() {
        QuestionSaveRequestDto dto = QuestionSaveRequestDto.builder()
                .title("spring")
                .content("spring 이 재밌어요!")
                .status(true)
                .fix(false)
                .build();

        dto.setWriterUser(writerUser);
        dto.setTargetUser(targetUser);

        Long questionId = questionService.save(dto);
        question = questionService.findById(questionId);

        targetUser = userService.findById(1L);
        writerUser = userService.findById(2L);
    }

    @Test
    @DisplayName("저장된 comment 을 조회하는 테스트")
    void findById() {

        // given
        CommentSaveRequestDto dto = CommentSaveRequestDto.builder()
                .questionId(1L)
                .writerUserId(writerUser.getId())
                .content("댓글")
                .parentCommentId(null)
                .build();

        Long saveId = commentService.save(dto);

        // when
        Comment comment = commentService.findById(saveId);

        // then
        assertAll(
                () -> assertEquals(comment.getId(), saveId),
                () -> assertEquals(comment.getContent(), dto.getContent())
        );
    }

    @Test
    @DisplayName("question id 로 소속된 comment 를 조회하는 테스트")
    void findByQuestionId() {

        // given
        CommentSaveRequestDto dto1 = CommentSaveRequestDto.builder()
                .questionId(1L)
                .writerUserId(writerUser.getId())
                .content("댓글1")
                .parentCommentId(null)
                .build();

        dto1.setQuestion(question);
        dto1.setWriterUser(writerUser);

        CommentSaveRequestDto dto2 = CommentSaveRequestDto.builder()
                .questionId(1L)
                .writerUserId(writerUser.getId())
                .content("댓글2")
                .parentCommentId(null)
                .build();

        dto2.setQuestion(question);
        dto2.setWriterUser(writerUser);

        Long saveId1 = commentService.save(dto1);
        Long saveId2 = commentService.save(dto2);

        // when
        List<CommentMainResponseDto> comments = commentService.findByQuestionId(1L);

        // then
        assertAll(
                () -> assertEquals(comments.size(), 2),
                () -> assertEquals(comments.get(0).getContent(), dto1.getContent()),
                () -> assertEquals(comments.get(1).getContent(), dto2.getContent())
        );
    }
}