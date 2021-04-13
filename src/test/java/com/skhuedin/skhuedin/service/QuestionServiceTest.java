package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.Question;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.dto.question.QuestionMainResponseDto;
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
class QuestionServiceTest {

    @Autowired UserService userService;
    @Autowired QuestionService questionService;

    User writerUser;
    User targetUser;

    @BeforeEach
    void beforeEach() {
        writerUser = userService.findById(1L);
        targetUser = userService.findById(2L);
    }

    @Test
    @DisplayName("question dto 를 question 테이블에 저장하는 테스트")
    void save() {

        // given
        QuestionSaveRequestDto dto = QuestionSaveRequestDto.builder()
                .title("spring")
                .content("spring 이 재밌어요!")
                .status(true)
                .fix(false)
                .build();

        // when
        Long saveId = questionService.save(dto);

        // then
        Question findQuestion = questionService.findById(saveId);
        assertAll(() -> {
            assertEquals(dto.getTitle(), findQuestion.getTitle());
            assertEquals(dto.getContent(), findQuestion.getContent());
            assertEquals(dto.getTargetUser(), findQuestion.getTargetUser());
            assertEquals(dto.getWriterUser(), findQuestion.getWriterUser());
        });
    }

    @Test
    @DisplayName("targetUser 의 id 를 활용하여 조회하는 테스트")
    void findByTargetUserIdDesc() {

        // given
        QuestionSaveRequestDto dto1 = QuestionSaveRequestDto.builder()
                .title("spring1")
                .content("spring 이 재밌어요!")
                .status(true)
                .fix(false)
                .build();

        QuestionSaveRequestDto dto2 = QuestionSaveRequestDto.builder()
                .title("spring2")
                .content("spring 이 너무 재밌어요!")
                .status(true)
                .fix(false)
                .build();

        questionService.save(dto1);
        questionService.save(dto2);
        
        // when
        List<QuestionMainResponseDto> questions = questionService.findByTargetUserIdDesc(targetUser.getId());
        
        // then
        assertEquals(questions.size(), 2);
    }
    
    @Test
    @DisplayName("question update 를 확인하는 테스트")
    void update() {
        
        // given
        QuestionSaveRequestDto saveDto = QuestionSaveRequestDto.builder()
                .title("spring1")
                .content("spring 이 재밌어요!")
                .status(true)
                .fix(false)
                .build();

        Long saveId = questionService.save(saveDto);

        // when
        QuestionSaveRequestDto updateDto = QuestionSaveRequestDto.builder()
                .title("spring2")
                .content("spring 이 너무 재밌어요!")
                .status(true)
                .fix(false)
                .build();

        Long updateId = questionService.update(saveId, updateDto);
        Question updateQuestion = questionService.findById(updateId);

        // then
        assertAll(
                () -> assertEquals(saveId, updateId),
                () -> assertEquals(updateQuestion.getTitle(), updateDto.getTitle()),
                () -> assertEquals(updateQuestion.getContent(), updateDto.getContent())
        );
    }

    @Test
    @DisplayName("question 삭제를 확인하는 테스트")
    void delete() {

        // given
        QuestionSaveRequestDto saveDto = QuestionSaveRequestDto.builder()
                .title("spring1")
                .content("spring 이 재밌어요!")
                .status(true)
                .fix(false)
                .build();

        Long saveId = questionService.save(saveDto);

        // when
        questionService.delete(saveId);

        // then
        assertThrows(IllegalArgumentException.class, () ->
                questionService.findById(saveId)
        );
    }

    @Test
    @DisplayName("조회수를 3 증가 시키는 테스트")
    void addView() {

        // given
        QuestionSaveRequestDto saveDto = QuestionSaveRequestDto.builder()
                .title("spring1")
                .content("spring 이 재밌어요!")
                .status(true)
                .fix(false)
                .build();

        Long saveId = questionService.save(saveDto);

        // when
        Question question = questionService.findById(saveId);

        question.addView();
        question.addView();
        question.addView();

        // then
        assertEquals(question.getView(), 3);
    }
}