package com.skhuedin.api.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SkillCategoryTest {

    @Test
    @DisplayName("skillCategory 를 생성하는 테스트")
    void createSkillCategory() {

        // given
        Talent talent = createTalent();
        String name = "Spring";

        // when
        SkillCategory skillCategory = SkillCategory.builder()
                .talent(talent)
                .name(name)
                .build();

        // then
        assertAll(() -> {
            assertEquals(skillCategory.getTalent(), talent);
            assertEquals(skillCategory.getName(), name);
        });
    }

    @Test
    @DisplayName("talent 를 누락하여 예외를 던지는 테스트")
    void createSkillCategoryTalentFalse() {

        // given
//        Talent talent = createTalent();
        String name = "Spring";

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            SkillCategory.builder()
//                .talent(talent)
                    .name(name)
                    .build();
        });
    }

    @Test
    @DisplayName("name 를 누락하여 예외를 던지는 테스트")
    void createSkillCategoryNameFalse() {

        // given
        Talent talent = createTalent();
//        String name = "Spring";

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            SkillCategory.builder()
                    .talent(talent)
//                    .name(name)
                    .build();
        });
    }

    @Test
    @DisplayName("skillCategory 를 update 하는 테스트")
    void updateSkillCategory() {

        // given
        Talent talent = createTalent();
        String name = "Spring";

        SkillCategory skillCategory = SkillCategory.builder()
                .talent(talent)
                .name(name)
                .build();

        // when
        String updatedName = "Java";

        SkillCategory updatedSkillCategory = SkillCategory.builder()
                .talent(talent)
                .name(updatedName)
                .build();

        skillCategory.updateSkillCategory(updatedSkillCategory);

        // then
        assertAll(() -> {
            assertEquals(updatedSkillCategory.getTalent(), talent);
            assertEquals(updatedSkillCategory.getName(), updatedName);
        });
    }

    private Talent createTalent() {

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

        return Talent.builder()
                .user(user)
                .talentImageUrl(talentImageUrl)
                .content(content)
                .entranceYear(entranceYear)
                .graduationYear(graduationYear)
                .startCareer(startCareer)
                .build();
    }
}