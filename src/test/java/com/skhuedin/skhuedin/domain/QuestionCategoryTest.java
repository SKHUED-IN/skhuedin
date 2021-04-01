package com.skhuedin.skhuedin.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionCategoryTest {

    @Test
    @DisplayName("QustionCategory_생성")
    void createQuestionCategory() {

        //given 어떤 값이 주어지고

        Question question = createQuestion();
        String name = "IT 직군";

        //when 무엇을 했을 때

        QuestionCategory questionCategory = createQuestionCategory(question, name);

        //then 어떤 값을 원한다.

        assertAll(() -> {
            assertEquals(question.getTitle(), questionCategory.getQuestion().getTitle());
            assertEquals(name, questionCategory.getName());
        });
    }

    @Test
    @DisplayName("question 누락 예외 발생 ")
    void createQuestionCategoryQuestionFalse() {

        //given 어떤 값이 주어지고

//        Question question = createQuestion();
        String name = "IT 직군";

        //when 무엇을 했을 때 //then 어떤 값을 원한다.

        assertThrows(
                IllegalArgumentException.class, () -> {
                    QuestionCategory questionCategory = QuestionCategory.builder()
//                            .question(question)
                            .name(name)
                            .build();
                });
    }

    @Test
    @DisplayName("name 누락 예외 발생 ")
    void createQuestionCategoryNameFalse() {

        //given 어떤 값이 주어지고

        Question question = createQuestion();
//        String name = "IT 직군";

        //when 무엇을 했을 때 //then 어떤 값을 원한다.

        assertThrows(
                IllegalArgumentException.class, () -> {
                    QuestionCategory questionCategory = QuestionCategory.builder()
                            .question(question)
//                            .name(name)
                            .build();
                });
    }

    QuestionCategory createQuestionCategory(Question question, String name) {
        return QuestionCategory.builder()
                .question(question)
                .name(name)
                .build();
    }

    Question createQuestion() {
        User targetUser = createUser("naver.com");
        User writerUser = createUser("google.com");

        return Question.builder()
                .targetUser(targetUser)
                .writerUser(writerUser)
                .title("title")
                .content("content")
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