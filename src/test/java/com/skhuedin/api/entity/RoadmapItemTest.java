package com.skhuedin.api.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RoadmapItemTest {

    @Test
    @DisplayName("roadmapItem 을 생성하는 테스트")
    void createRoadmapItem() {
        
        // given
        Roadmap roadmap = createRoadmap();
        String content = "학교 입학";
        LocalDate yearMonth = LocalDate.of(2016, 3, 2);

        // when
        RoadmapItem roadmapItem = RoadmapItem.builder()
                .roadmap(roadmap)
                .content(content)
                .yearMonth(yearMonth)
                .build();

        // then
        assertAll(() -> {
            assertEquals(roadmapItem.getRoadmap(), roadmap);
            assertEquals(roadmapItem.getContent(), content);
            assertEquals(roadmapItem.getYearMonth(), yearMonth);
        });
    }

    @Test
    @DisplayName("roadmap 을 누락하여 예외를 던지는 테스트")
    void createRoadmapItemRoadmapFalse() {

        // given
//        Roadmap roadmap = createRoadmap();
        String content = "학교 입학";
        LocalDate yearMonth = LocalDate.of(2016, 3, 2);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            RoadmapItem.builder()
//                    .roadmap(roadmap)
                    .content(content)
                    .yearMonth(yearMonth)
                    .build();
        });
    }

    @Test
    @DisplayName("content 을 누락하여 예외를 던지는 테스트")
    void createRoadmapItemContentFalse() {

        // given
        Roadmap roadmap = createRoadmap();
//        String content = "학교 입학";
        LocalDate yearMonth = LocalDate.of(2016, 3, 2);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            RoadmapItem.builder()
                    .roadmap(roadmap)
//                    .content(content)
                    .yearMonth(yearMonth)
                    .build();
        });
    }

    @Test
    @DisplayName("yearMonth 를 누락하여 예외를 던지는 테스트")
    void createRoadmapItemYearMonthFalse() {

        // given
        Roadmap roadmap = createRoadmap();
        String content = "학교 입학";
//        LocalDate yearMonth = LocalDate.of(2016, 3, 2);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            RoadmapItem.builder()
                    .roadmap(roadmap)
                    .content(content)
//                    .yearMonth(yearMonth)
                    .build();
        });
    }

    @Test
    @DisplayName("roadmapItem 을 update 하는 테스트")
    void updateRoadmapItem() {

        // given
        Roadmap roadmap = createRoadmap();
        String content = "학교 입학";
        LocalDate yearMonth = LocalDate.of(2016, 3, 2);

        RoadmapItem roadmapItem = RoadmapItem.builder()
                .roadmap(roadmap)
                .content(content)
                .yearMonth(yearMonth)
                .build();

        // when
        String updatedContent = "대외활동 시작";
        LocalDate updatedYearMonth = LocalDate.of(2021, 10, 1);

        RoadmapItem updatedRoadmapItem = RoadmapItem.builder()
                .roadmap(roadmap)
                .content(updatedContent)
                .yearMonth(updatedYearMonth)
                .build();

        roadmapItem.updateRoadmapItem(updatedRoadmapItem);

        // then
        assertAll(() -> {
            assertEquals(roadmapItem.getRoadmap(), roadmap);
            assertEquals(roadmapItem.getContent(), updatedContent);
            assertEquals(roadmapItem.getYearMonth(), updatedYearMonth);
        });
        
    }
    
    private Roadmap createRoadmap() {
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

        return Roadmap.builder()
                .talent(talent)
                .build();
    }
}