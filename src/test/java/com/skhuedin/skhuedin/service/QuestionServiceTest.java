package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.Question;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.dto.question.QuestionMainResponseDto;
import com.skhuedin.skhuedin.dto.question.QuestionSaveRequestDto;
import com.skhuedin.skhuedin.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class QuestionServiceTest {

    @Autowired UserRepository userRepository;
    @Autowired QuestionService questionService;

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
}