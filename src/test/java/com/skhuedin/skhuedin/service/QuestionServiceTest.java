package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.dto.question.QuestionMainResponseDto;
import com.skhuedin.skhuedin.dto.question.QuestionSaveRequestDto;
import com.skhuedin.skhuedin.repository.QuestionRepository;
import com.skhuedin.skhuedin.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuestionServiceTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionService questionService;

    User targetUser;
    User writerUser;

    @BeforeEach
    void beforeEach() {
        questionRepository.deleteAll();
        userRepository.deleteAll();

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
    @DisplayName("dto 를 받아 question 을 저장하고 조회하는 테스트")
    void save() {

        // given
        QuestionSaveRequestDto requestDto = getRequestDto();

        // when
        Long saveId = questionService.save(requestDto);
        QuestionMainResponseDto responseDto = questionService.findById(saveId);

        // then
        assertAll(
                () -> assertEquals(responseDto.getId(), saveId),
                () -> assertEquals(responseDto.getTargetUser().getId(), requestDto.getTargetUserId()),
                () -> assertEquals(responseDto.getWriterUser().getId(), requestDto.getWriterUserId()),
                () -> assertEquals(responseDto.getTitle(), requestDto.getTitle()),
                () -> assertEquals(responseDto.getContent(), requestDto.getContent()),
                () -> assertEquals(responseDto.getStatus(), requestDto.getStatus()),
                () -> assertEquals(responseDto.getFix(), requestDto.getFix())
        );
    }

    @Test
    @DisplayName("존재하지 않는 user 로 인하여 저장하던 중 예외를 던지는 테스트")
    void save_false() {

        // given
        QuestionSaveRequestDto requestDto = QuestionSaveRequestDto.builder()
                .targetUserId(10L) // 존재하지 않는 유저
                .writerUserId(writerUser.getId())
                .title("Spring")
                .content("Spring 은 어떤식으로 공부하는 것이 좋을까요?")
                .status(false)
                .fix(false)
                .build();

        // when & then
        assertThrows(IllegalArgumentException.class, () ->
                questionService.save(requestDto)
        );
    }

    @Test
    @DisplayName("question 을 갱신하고 조회하는 테스트")        
    void update() {

        // given
        QuestionSaveRequestDto requestDto = getRequestDto();

        QuestionSaveRequestDto updateDto = QuestionSaveRequestDto.builder()
                .targetUserId(targetUser.getId())
                .writerUserId(writerUser.getId())
                .title("Spring test")
                .content("test code 작성 꿀팁이 궁금합니다!")
                .status(false)
                .fix(false)
                .build();
                
        // when
        Long saveId = questionService.save(requestDto);
        Long updateId = questionService.update(saveId, updateDto);
        QuestionMainResponseDto responseDto = questionService.findById(updateId);

        // then
        assertAll(
                () -> assertEquals(updateId, saveId),
                () -> assertEquals(responseDto.getTargetUser().getId(), updateDto.getTargetUserId()),
                () -> assertEquals(responseDto.getWriterUser().getId(), updateDto.getWriterUserId()),
                () -> assertEquals(responseDto.getTitle(), updateDto.getTitle()),
                () -> assertEquals(responseDto.getContent(), updateDto.getContent()),
                () -> assertEquals(responseDto.getStatus(), updateDto.getStatus()),
                () -> assertEquals(responseDto.getFix(), updateDto.getFix())
        );
    }

    @Test
    @DisplayName("question 을 갱신하던 중 존재하지 않는 user 로 인하여 예외를 던지는 테스트")
    void update_user_false() {

        // given
        QuestionSaveRequestDto requestDto = getRequestDto();

        QuestionSaveRequestDto updateDto = QuestionSaveRequestDto.builder()
                .targetUserId(20L) // 존재하지 않는 유저
                .writerUserId(writerUser.getId())
                .title("Spring test")
                .content("test code 작성 꿀팁이 궁금합니다!")
                .status(false)
                .fix(false)
                .build();

        // when & then
        Long saveId = questionService.save(requestDto);
        assertThrows(IllegalArgumentException.class, () ->
             questionService.update(saveId, updateDto)
        );
    }

    @Test
    @DisplayName("question 을 삭제하는 테스트")
    void delete() {

        // given
        QuestionSaveRequestDto requestDto = getRequestDto();
        Long saveId = questionService.save(requestDto);

        // when
        questionService.delete(saveId);

        // then
        assertEquals(questionRepository.findAll().size(), 0);
    }

    @Test
    @DisplayName("존재하지 않는 question 을 삭제하여 예외를 던지는 테스트")
    void delete_false() {

        // given & when & then
        assertThrows(IllegalArgumentException.class, () ->
                questionService.delete(100L)
        );
    }

    @Test
    @DisplayName("target user id 로 question 을 조회하는 테스트")
    void findByTargetUserId() {

        // given
        QuestionSaveRequestDto requestDto1 = getRequestDto();
        QuestionSaveRequestDto requestDto2 = getRequestDto();
        QuestionSaveRequestDto requestDto3 = getRequestDto();

        questionService.save(requestDto1);
        questionService.save(requestDto2);
        questionService.save(requestDto3);

        // when
        List<QuestionMainResponseDto> questions = questionService.findByTargetUserId(targetUser.getId());

        // then
        assertEquals(questions.size(), 3);
    }

    private QuestionSaveRequestDto getRequestDto() {
        return QuestionSaveRequestDto.builder()
                .targetUserId(targetUser.getId())
                .writerUserId(writerUser.getId())
                .title("Spring")
                .content("Spring 은 어떤식으로 공부하는 것이 좋을까요?")
                .status(false)
                .fix(false)
                .build();
    }

    @AfterEach
    void afterEach() {
        questionRepository.deleteAll();
        userRepository.deleteAll();
    }
}