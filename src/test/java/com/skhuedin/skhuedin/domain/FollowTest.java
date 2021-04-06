package com.skhuedin.skhuedin.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FollowTest {

    @Test
    @DisplayName("follow_생성")
    public void createFollow() {

        //given 어떤 값이 주어지고

        User user = createUser();

        //when 무엇을 했을 때

        Follow follow = Follow.builder()
                .user(user)
                .build();

        //then 어떤 값을 원한다.

        assertAll(() -> {
            assertEquals(user.getId(), follow.getUser().getId());
        });
    }

    @Test
    @DisplayName("user 를 누락하여 예외를 발생 시키는 테스")
    public void createFollowUserFalse() {

        //given 어떤 값이 주어지고

//        User user = createUser();

        //when 무엇을 했을 때 //then 어떤 값을 원한다.

        Assertions.assertThrows(
                IllegalArgumentException.class, () -> {
                    Follow follow = Follow.builder()
//                            .user(user)
                            .build();
                });
    }

    User createUser() {

        return User.builder()
                .email("google.kakao.naver")
                .password("test123")
                .provider(Provider.GOOGLE)
                .userImageUrl("photo.asnvsd")
                .build();
    }
}