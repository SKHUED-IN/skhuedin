package com.skhuedin.skhuedin.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    @Test
    @DisplayName("question_생성")
    void createQuestion() {

        //given 어떤 값이 주어지고

        User targetUser = createUser("naver.com");
        User writerUser = createUser("google.com");
        String title = "테스트 코드 생성에 대해 질문 있습니다. ";
        String content = " 테스트 코드를 처음 짜보는데,, 잘하고 있는건가요?";

        //when 무엇을 했을 때

        Question question = Question.builder()
                .targetUser(targetUser)
                .writerUser(writerUser)
                .title(title)
                .content(content)
                .build();

        //then 어떤 값을 원한다.

        assertAll(() -> {
            assertEquals(targetUser.getEmail(), question.getTargetUser().getEmail());
            assertEquals(writerUser.getEmail(), question.getWriterUser().getEmail());
            assertEquals(title, question.getTitle());
            assertEquals(content, question.getContent());
        });
    }

    @Test
    @DisplayName("targetUser 누락으로 인한 예외")
    void createQuestionTargetUserFalse() {

        //given 어떤 값이 주어지고

//        User targetUser = createUser("naver.com");
        User writerUser = createUser("google.com");
        String title = "테스트 코드 생성에 대해 질문 있습니다. ";
        String content = " 테스트 코드를 처음 짜보는데,, 잘하고 있는건가요?";

        //when 무엇을 했을 때 //then 어떤 값을 원한다.

        Assertions.assertThrows(
                IllegalArgumentException.class, () -> {
                    Question question = Question.builder()
//                .targetUser(targetUser)
                            .writerUser(writerUser)
                            .title(title)
                            .content(content)
                            .build();
                });
    }

    @Test
    @DisplayName("writerUser 누락으로 인한 예외")
    void createQuestionWriterUserFalse() {

        //given 어떤 값이 주어지고

        User targetUser = createUser("naver.com");
//        User writerUser = createUser("google.com");
        String title = "테스트 코드 생성에 대해 질문 있습니다. ";
        String content = " 테스트 코드를 처음 짜보는데,, 잘하고 있는건가요?";

        //when 무엇을 했을 때 //then 어떤 값을 원한다.

        Assertions.assertThrows(
                IllegalArgumentException.class, () -> {
                    Question question = Question.builder()
                            .targetUser(targetUser)
//                            .writerUser(writerUser)
                            .title(title)
                            .content(content)
                            .build();
                });
    }

    @Test
    @DisplayName("title 누락으로 인한 예외")
    void createQuestionTitleFalse() {

        //given 어떤 값이 주어지고

        User targetUser = createUser("naver.com");
        User writerUser = createUser("google.com");
//        String title = "테스트 코드 생성에 대해 질문 있습니다. ";
        String content = " 테스트 코드를 처음 짜보는데,, 잘하고 있는건가요?";

        //when 무엇을 했을 때 //then 어떤 값을 원한다.

        Assertions.assertThrows(
                IllegalArgumentException.class, () -> {
                    Question question = Question.builder()
                            .targetUser(targetUser)
                            .writerUser(writerUser)
//                            .title(title)
                            .content(content)
                            .build();
                });
    }

    @Test
    @DisplayName("content 누락으로 인한 예외")
    void createQuestionContentFalse() {

        //given 어떤 값이 주어지고

        User targetUser = createUser("naver.com");
        User writerUser = createUser("google.com");
        String title = "테스트 코드 생성에 대해 질문 있습니다. ";
//        String content = " 테스트 코드를 처음 짜보는데,, 잘하고 있는건가요?";

        //when 무엇을 했을 때 //then 어떤 값을 원한다.

        Assertions.assertThrows(
                IllegalArgumentException.class, () -> {
                    Question question = Question.builder()
                            .targetUser(targetUser)
                            .writerUser(writerUser)
                            .title(title)
//                            .content(content)
                            .build();
                });
    }

    @Test
    @DisplayName("question update 확인")
    void updateQuestion() {

        //given 어떤 값이 주어지고

        User targetUser = createUser("naver.com");
        User writerUser = createUser("google.com");
        String title = "테스트 코드 생성에 대해 질문 있습니다. ";
        String content = " 테스트 코드를 처음 짜보는데,, 잘하고 있는건가요?";

        Question question = createQuestion(targetUser, writerUser, title, content);

        //when 무엇을 했을 때

        User updateTargetUser = createUser("google.com");
        User updateWriterUser = createUser("naver.com");
        String updateTitle = " 본질적인 테스트 코드에 관하여 ";
        String updateContent = " 엔티티를 설계부터 신중하게 해야한다고 생각합니다. ";

        Question updateQuestion = createQuestion(updateTargetUser, updateWriterUser, updateTitle, updateContent);
        question.updateQuestion(updateQuestion);

        //then 어떤 값을 원한다.

        assertAll(() -> {
            assertEquals(updateTargetUser.getEmail(), question.getTargetUser().getEmail());
            assertEquals(updateWriterUser.getEmail(), question.getWriterUser().getEmail());
            assertEquals(updateTitle, question.getTitle());
            assertEquals(updateContent, question.getContent());
        });
    }

    Question createQuestion(User targetUser, User writerUser, String title, String content) {

        return Question.builder()
                .targetUser(targetUser)
                .writerUser(writerUser)
                .title(title)
                .content(content)
                .build();
    }

    User createUser(String email) {

        return User.builder()
                .email(email)
                .password("test123")
                .provider(Provider.GOOGLE)
                .userImageUrl("photo.asnvsd")
                .build();
    }
}