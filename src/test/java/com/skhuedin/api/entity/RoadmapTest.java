package com.skhuedin.api.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RoadmapTest {

    @Test
    @DisplayName("roadmap 을 생성하는 테스트")
    void createRoadmap() {

        // given
        User user = User.builder()
                .email("hyeonic@email.com")
                .password("1234")
                .provider(Provider.GOOGLE)
                .userImageUrl("/img")
                .build();

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
        Roadmap roadmap = Roadmap.builder()
                .talent(talent)
                .build();

        // then
        assertEquals(roadmap.getTalent(), talent);
    }
}