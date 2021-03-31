package com.skhuedin.api.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TalentTest {

    @Test
    @DisplayName("talent 를 생성하는 테스트")
    void createTalent() {

        // given
        User user = createUser();

        String talentImageUrl = "/talent/img";
        String content = "지식을 널리 알리고 싶은 자바 개발자";
        LocalDateTime entranceYear = LocalDateTime.of(2016, 3, 1, 0, 0);
        LocalDateTime graduationYear = LocalDateTime.of(2020, 2, 1, 0, 0);
        LocalDateTime startCareer = LocalDateTime.of(2021, 3, 1, 0, 0);

        // when
        Talent talent = Talent.builder()
                .user(user)
                .talentImageUrl(talentImageUrl)
                .content(content)
                .entranceYear(entranceYear)
                .graduationYear(graduationYear)
                .startCareer(startCareer)
                .build();

        // then
        assertAll(() -> {
            assertEquals(user, talent.getUser());
            assertEquals(talentImageUrl, talent.getTalentImageUrl());
            assertEquals(content, talent.getContent());
            assertEquals(entranceYear, talent.getEntranceYear());
            assertEquals(graduationYear, talent.getGraduationYear());
            assertEquals(startCareer, talent.getStartCareer());
        });
    }

    @Test
    @DisplayName("user 를 누락하여 예외를 던지는 테스트")
    void createTalentUserFalse() {

        // given
//        User user = createUser();

        String talentImageUrl = "/talent/img";
        String content = "지식을 널리 알리고 싶은 자바 개발자";
        LocalDateTime entranceYear = LocalDateTime.of(2016, 3, 1, 0, 0);
        LocalDateTime graduationYear = LocalDateTime.of(2020, 2, 1, 0, 0);
        LocalDateTime startCareer = LocalDateTime.of(2021, 3, 1, 0, 0);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
                Talent.builder()
//                        .user(user)
                        .talentImageUrl(talentImageUrl)
                        .content(content)
                        .entranceYear(entranceYear)
                        .graduationYear(graduationYear)
                        .startCareer(startCareer)
                        .build();
        });
    }

    @Test
    @DisplayName("entranceDay 를 누락하여 예외를 던지는 테스트")
    void createTalentEntranceYearFalse() {

        // given
        User user = createUser();

        String talentImageUrl = "/talent/img";
        String content = "지식을 널리 알리고 싶은 자바 개발자";
//        LocalDateTime entranceYear = LocalDateTime.of(2016, 3, 1, 0, 0);
        LocalDateTime graduationYear = LocalDateTime.of(2020, 2, 1, 0, 0);
        LocalDateTime startCareer = LocalDateTime.of(2021, 3, 1, 0, 0);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            Talent.builder()
                    .user(user)
                    .talentImageUrl(talentImageUrl)
                    .content(content)
//                    .entranceYear(entranceYear)
                    .graduationYear(graduationYear)
                    .startCareer(startCareer)
                    .build();
        });
    }

    @Test
    @DisplayName("graduationYear 를 누락하여 예외를 던지는 테스트")
    void createTalentGraduationYearFalse() {

        // given
        User user = createUser();

        String talentImageUrl = "/talent/img";
        String content = "지식을 널리 알리고 싶은 자바 개발자";
        LocalDateTime entranceYear = LocalDateTime.of(2016, 3, 1, 0, 0);
//        LocalDateTime graduationYear = LocalDateTime.of(2020, 2, 1, 0, 0);
        LocalDateTime startCareer = LocalDateTime.of(2021, 3, 1, 0, 0);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            Talent.builder()
                    .user(user)
                    .talentImageUrl(talentImageUrl)
                    .content(content)
                    .entranceYear(entranceYear)
//                    .graduationYear(graduationYear)
                    .startCareer(startCareer)
                    .build();
        });
    }

    @Test
    @DisplayName("startCareer 를 누락하여 예외를 던지는 테스트")
    void createTalentStartCareerFalse() {

        // given
        User user = createUser();

        String talentImageUrl = "/talent/img";
        String content = "지식을 널리 알리고 싶은 자바 개발자";
        LocalDateTime entranceYear = LocalDateTime.of(2016, 3, 1, 0, 0);
        LocalDateTime graduationYear = LocalDateTime.of(2020, 2, 1, 0, 0);
//        LocalDateTime startCareer = LocalDateTime.of(2021, 3, 1, 0, 0);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            Talent.builder()
                    .user(user)
                    .talentImageUrl(talentImageUrl)
                    .content(content)
                    .entranceYear(entranceYear)
                    .graduationYear(graduationYear)
//                    .startCareer(startCareer)
                    .build();
        });
    }

    @Test
    @DisplayName("talent 를 update 하는 테스트")
    void updateTalent() {

        // given
        User user = createUser();

        String talentImageUrl = "/talent/img";
        String content = "지식을 널리 알리고 싶은 자바 개발자";
        LocalDateTime entranceYear = LocalDateTime.of(2016, 3, 1, 0, 0);
        LocalDateTime graduationYear = LocalDateTime.of(2020, 2, 1, 0, 0);
        LocalDateTime startCareer = LocalDateTime.of(2021, 3, 1, 0, 0);

        Talent talent = Talent.builder()
                .user(user)
                .talentImageUrl(talentImageUrl)
                .content(content)
                .entranceYear(entranceYear)
                .graduationYear(graduationYear)
                .startCareer(startCareer)
                .build();

        // when
        String updatedTalentImageUrl = "/talent/img";
        String updatedContent = "지식을 널리 알리고 싶은 자바 개발자";
        LocalDateTime updatedEntranceYear = LocalDateTime.of(2016, 3, 1, 0, 0);
        LocalDateTime updatedGraduationYear = LocalDateTime.of(2020, 2, 1, 0, 0);
        LocalDateTime updatedStartCareer = LocalDateTime.of(2021, 3, 1, 0, 0);

        Talent updatedTalent = Talent.builder()
                .user(user)
                .talentImageUrl(updatedTalentImageUrl)
                .content(updatedContent)
                .entranceYear(updatedEntranceYear)
                .graduationYear(updatedGraduationYear)
                .startCareer(updatedStartCareer)
                .build();

        talent.updateTalent(updatedTalent);

        // then
        assertAll(() -> {
            assertEquals(user, talent.getUser());
            assertEquals(updatedTalentImageUrl, talent.getTalentImageUrl());
            assertEquals(updatedContent, talent.getContent());
            assertEquals(updatedEntranceYear, talent.getEntranceYear());
            assertEquals(updatedGraduationYear, talent.getGraduationYear());
            assertEquals(updatedStartCareer, talent.getStartCareer());
        });
    }

    @Test
    @DisplayName("view 의 값을 증가시키는 테스트")
    void addView() {

        // given
        User user = createUser();

        String talentImageUrl = "/talent/img";
        String content = "지식을 널리 알리고 싶은 자바 개발자";
        LocalDateTime entranceYear = LocalDateTime.of(2016, 3, 1, 0, 0);
        LocalDateTime graduationYear = LocalDateTime.of(2020, 2, 1, 0, 0);
        LocalDateTime startCareer = LocalDateTime.of(2021, 3, 1, 0, 0);

        Talent talent = Talent.builder()
                .user(user)
                .talentImageUrl(talentImageUrl)
                .content(content)
                .entranceYear(entranceYear)
                .graduationYear(graduationYear)
                .startCareer(startCareer)
                .build();

        // when
        talent.addView();
        talent.addView();

        // then
        assertEquals(talent.getView(), 2);
    }

    private User createUser() {
        return User.builder()
                .email("hyeonic@email.com")
                .password("1234")
                .provider(Provider.GOOGLE)
                .userImageUrl("/img")
                .build();
    }
}