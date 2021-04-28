package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;
import com.skhuedin.skhuedin.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    User user;
    @BeforeEach
    void beforeEach() {
        user = User.builder()
                .name("홍길동")
                .email("hong@email.com")
                .password("1234")
                .userImageUrl("/img")
                .entranceYear(LocalDateTime.now())
                .graduationYear(LocalDateTime.now())
                .provider(Provider.KAKAO)
                .build();
    }

    @Test
    @DisplayName("user id 를 활용하여 조회하는 테스트")
    void findById() {

        // given
        User user = User.builder()
                .name("홍길동")
                .email("hong@email.com")
                .password("1234")
                .userImageUrl("/img")
                .entranceYear(LocalDateTime.now())
                .graduationYear(LocalDateTime.now())
                .provider(Provider.KAKAO)
                .build();

        User saveUser = userRepository.save(user);

        // when
        UserMainResponseDto responseDto = userService.findById(saveUser.getId());

        // then
        assertAll(
                () -> assertEquals(responseDto.getId(), saveUser.getId()),
                () -> assertEquals(responseDto.getEmail(), saveUser.getEmail()),
                () -> assertEquals(responseDto.getName(), saveUser.getName())
        );
    }

    @Test
    @DisplayName("존재하지 않는 user id 를 조회했을 때 예외를 던지는 테스트")
    void findById_false() {

        // then
        assertThrows(IllegalArgumentException.class, () -> userService.findById(1L));
    }

    @Test
    @DisplayName("equals 가 잘 동작하는지 확인함")
    void equalsTest() {

        //given 어떤 값이 주어지고
       User user2 = User.builder()
                .name("홍길동")
                .email("hong@email.com")
                .password("1234")
                .userImageUrl("/img")
                .entranceYear(LocalDateTime.now())
                .graduationYear(LocalDateTime.now())
                .provider(Provider.KAKAO)
                .build();
        //when 무엇을 했을 때 then 어떤 값을 원한다.
        Assertions.assertThat(userService.checkUpdate(user,user2)).isEqualTo(true);
    }

    @AfterEach
    void afterEach() {
        userRepository.deleteAll();
    }
}