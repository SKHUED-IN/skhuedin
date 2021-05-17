package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;
import com.skhuedin.skhuedin.dto.user.UserSaveRequestDto;
import com.skhuedin.skhuedin.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql("/truncate.sql")
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
    @DisplayName("입학년도, 졸업년도를 받아 업데이트가 되는 지 확인")
    @Disabled
    void updateAddUserInfo() {

        //given 어떤 값이 주어지고

        User user = User.builder()
                .name("홍길동")
                .email("hong@email.com")
                .password("1234")
                .userImageUrl("/img")
                .entranceYear(LocalDateTime.now().plusDays(1))
                .graduationYear(LocalDateTime.now().plusDays(2))
                .provider(Provider.KAKAO)
                .build();

        User saveUser = userRepository.save(user);

        UserSaveRequestDto requestDto = UserSaveRequestDto.builder()
                .entranceYear(LocalDateTime.now())
                .graduationYear(LocalDateTime.now())
                .build();

        //when 무엇을 했을 때

        userService.updateInfo(saveUser.getId(), requestDto);
        User user1 = userService.getUser(saveUser.getId());

        //then 어떤 값을 원한다.

        assertAll(

                () -> assertEquals(requestDto.getEntranceYear(), user1.getEntranceYear()),
                () -> assertEquals(requestDto.getGraduationYear(), user1.getGraduationYear()),
                // 내가 요청한  입학년도, 졸업년도가 잘 업데이트가 되었는가.
                () -> assertEquals(saveUser.getEmail(), user1.getEmail()),
                // 입학년도, 졸업년도 외에 다른 정보는 변함이 없다.
                () -> assertNotSame(saveUser.getGraduationYear(), user1.getGraduationYear())
                //맨처음 가입했을 때 입학년도, 졸업년도와 update 이후 졸업년도가 같지 않다.
        );
    }

    @Test
    @DisplayName("존재하지 않는 user id 를 조회했을 때 예외를 던지는 테스트")
    void findById_false() {

        // then
        assertThrows(IllegalArgumentException.class, () -> userService.findById(0L));
    }


    @AfterEach
    void afterEach() {
        userRepository.deleteAll();
    }
}