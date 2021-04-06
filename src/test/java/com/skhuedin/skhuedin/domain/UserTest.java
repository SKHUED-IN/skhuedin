package com.skhuedin.skhuedin.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    @Test
    @DisplayName("user_생성")
    public void createUser() {

        //given 어떤 값이 주어지고

        String email = "google.kakao.naver";
        String password = "test123";
        Provider provider = Provider.GOOGLE;
        String userImageUrl = "photo.asnvsd";

        //when 무엇을 했을 때

        User user = createUser(email, password, provider, userImageUrl);

        //then 어떤 값을 원한다.

        assertAll(() -> {
            assertEquals(email, user.getEmail());
            assertEquals(password, user.getPassword());
            assertEquals(provider, user.getProvider());
            assertEquals(userImageUrl, user.getUserImageUrl());
        });
    }

    @Test
    @DisplayName("email 을 누락하여 예외를 던지는 테스")
    public void createUserEmailFalse() {

        //given 어떤 값이 주어지고

//        String email = "google.kakao.naver";
        String password = "test123";
        Provider provider = Provider.GOOGLE;
        String userImageUrl = "photo.asnvsd";

        //when 무엇을 했을 때 //then 어떤 값을 원한다.

        Assertions.assertThrows(
                IllegalArgumentException.class, () -> {
                    User user = User.builder()
//                            .email(email)
                            .password(password)
                            .provider(provider)
                            .userImageUrl(userImageUrl)
                            .build();
                });
    }

    @Test
    @DisplayName("password 를 누락하여 예외를 던지는 테스")
    public void createUserPasswordFalse() {

        //given 어떤 값이 주어지고

        String email = "google.kakao.naver";
//        String password = "test123";
        Provider provider = Provider.GOOGLE;
        String userImageUrl = "photo.asnvsd";

        //when 무엇을 했을 때 //then 어떤 값을 원한다.

        Assertions.assertThrows(
                IllegalArgumentException.class, () -> {
                    User user = User.builder()
                            .email(email)
//                            .password(password)
                            .provider(provider)
                            .userImageUrl(userImageUrl)
                            .build();
                });
    }

    @Test
    @DisplayName("provider 를 누락하여 예외를 던지는 테스")
    public void createUserProviderFalse() {

        //given 어떤 값이 주어지고

        String email = "google.kakao.naver";
        String password = "test123";
//        Provider provider = Provider.GOOGLE;
        String userImageUrl = "photo.asnvsd";

        //when 무엇을 했을 때 //then 어떤 값을 원한다.

        Assertions.assertThrows(
                IllegalArgumentException.class, () -> {
                    User user = User.builder()
                            .email(email)
                            .password(password)
//                            .provider(provider)
                            .userImageUrl(userImageUrl)
                            .build();
                });
    }

    @Test
    @DisplayName("userImageUrl 를 누락하여 예외를 던지는 테스")
    public void createUserUserImageUrlFalse() {

        //given 어떤 값이 주어지고

        String email = "google.kakao.naver";
        String password = "test123";
        Provider provider = Provider.GOOGLE;
//        String userImageUrl = "photo.asnvsd";

        //when 무엇을 했을 때 //then 어떤 값을 원한다.

        Assertions.assertThrows(
                IllegalArgumentException.class, () -> {
                    User user = User.builder()
                            .email(email)
                            .password(password)
                            .provider(provider)
//                            .userImageUrl(userImageUrl)
                            .build();
                });
    }

    @Test
    @DisplayName("user update 가 정상적으로 작동하는지 확인하는 테스")
    public void updateUser() {

        //given 어떤 값이 주어지고

        String email = "google.kakao.naver";
        String password = "test123";
        Provider provider = Provider.GOOGLE;
        String userImageUrl = "photo.asnvsd";

        User user = createUser(email, password, provider, userImageUrl);

        //when 무엇을 했을 때

        String updateEmail = "naver.com";
        String updatePassword = "abc123";
        Provider updateProvider = Provider.SELF;
        String updateUserImageUrl = "photo.SUDAL";

        User updateUser = createUser(email, password, provider, userImageUrl);
        user.updateUser(updateUser);

        //then 어떤 값을 원한다.

        assertAll(() -> {
            assertEquals(updateEmail, user.getEmail());
            assertEquals(updatePassword, user.getPassword());
            assertEquals(updateProvider, user.getProvider());
            assertEquals(updateUserImageUrl, user.getUserImageUrl());
        });
    }

    User createUser(String email, String password, Provider provider, String userImageUrl) {

        return User.builder()
                .email(email)
                .password(password)
                .provider(provider)
                .userImageUrl(userImageUrl)
                .build();
    }
}