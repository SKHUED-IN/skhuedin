package com.skhuedin.skhuedin.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CommentTest {

    User writerUser;
    User targetUser;
    Question question;

    @BeforeEach
    void beforeEach() {
        writerUser = User.builder()
                .email("writer@email.com")
                .name("홍길동")
                .password("1234")
                .provider(Provider.KAKAO)
                .entranceYear(LocalDateTime.of(2016, 3, 2, 0, 0))
                .graduationYear(LocalDateTime.of(2020, 2, 2, 0, 0))
                .userImageUrl("/img")
                .build();

        targetUser = User.builder()
                .email("target@email.com")
                .name("전우치")
                .password("1234")
                .provider(Provider.KAKAO)
                .entranceYear(LocalDateTime.of(2016, 3, 2, 0, 0))
                .graduationYear(LocalDateTime.of(2020, 2, 2, 0, 0))
                .userImageUrl("/img")
                .build();

        question = Question.builder()
                .title("진로")
                .content("진로를 찾게된 계기는 무엇인가요?")
                .writerUser(writerUser)
                .targetUser(targetUser)
                .build();
    }

    @Test
    @DisplayName("comment 를 생성하는 테스트")
    void createComment() {

        // given
        String content = "추가적으로 궁금한 점은 이메일로 연락드려도 될까요?";

        // when
        Comment comment = Comment.builder()
                .question(question)
                .content(content)
                .writerUser(writerUser)
                .build();

        // then
        assertAll(
                () -> assertEquals(comment.getQuestion(), question),
                () -> assertEquals(comment.getContent(), content),
                () -> assertEquals(comment.getWriterUser(), writerUser)
        );
    }

    @Test
    @DisplayName("comment 의 내용을 수정하는 테스트")
    void updateComment() {

        // given
        Comment comment = Comment.builder()
                .question(question)
                .content("추가적으로 궁금한 점은 이메일로 연락드려도 될까요?")
                .writerUser(writerUser)
                .build();

        String updateContent = "추가적으로 궁금한 점은 이메일이 아닌 카톡으로 드려도 될까요?";
        Comment updateComment = Comment.builder()
                .question(question)
                .content(updateContent)
                .writerUser(writerUser)
                .build();

        // when
        comment.updateComment(updateComment);
        
        // then
        assertAll(
                () -> assertEquals(comment.getContent(), updateContent)
        );
    }

    @Test
    @DisplayName("parent 댓글에 child 댓글 3개를 추가하는 테스트")
    void addChildComment() {

        // given
        Comment parent = Comment.builder()
                .question(question)
                .content("추가적으로 궁금한 점은 이메일로 연락드려도 될까요?")
                .writerUser(writerUser)
                .build();

        Comment child1 = Comment.builder()
                .question(question)
                .content("대댓글1")
                .writerUser(writerUser)
                .build();

        Comment child2 = Comment.builder()
                .question(question)
                .content("대댓글2")
                .writerUser(writerUser)
                .build();

        Comment child3 = Comment.builder()
                .question(question)
                .content("대댓글3")
                .writerUser(writerUser)
                .build();

        // when
        parent.addChild(child1);
        parent.addChild(child2);
        parent.addChild(child3);

        // then
        assertAll(
                () -> assertEquals(parent.getChildren().size(), 3),
                () -> assertEquals(parent.getChildren().get(0), child1),
                () -> assertEquals(parent.getChildren().get(1), child2),
                () -> assertEquals(parent.getChildren().get(2), child3)
        );
    }
}