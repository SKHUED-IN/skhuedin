package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.domain.user.Role;
import com.skhuedin.skhuedin.domain.user.User;
import com.skhuedin.skhuedin.dto.user.UserAdminMainResponseDto;
import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;
import com.skhuedin.skhuedin.dto.user.UserUpdateDto;
import com.skhuedin.skhuedin.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
                .userImageUrl("/img")
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
                .userImageUrl("/img")
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

        //given
        User user = User.builder()
                .name("홍길동")
                .email("hong@email.com")
                .userImageUrl("/img")
                .provider(Provider.KAKAO)
                .build();

        User saveUser = userRepository.save(user);

        UserUpdateDto requestDto = UserUpdateDto.builder()
                .entranceYear("2010")
                .graduationYear("2020")
                .build();

        //when
        userService.updateYearData(saveUser.getId(), requestDto);
        User user1 = userService.getUser(saveUser.getId());

        //then
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

    @Test
    @DisplayName("user 목록을 paging하여 조회하는 테스트")
    void findAll() {

        // given
        for (int i = 0; i < 10; i++) {
            User user = generateUser(i);
            userRepository.save(user);
        }

        // when
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<UserAdminMainResponseDto> users = userService.findAll(pageRequest);

        // then
        assertAll(
                () -> assertEquals(users.getContent().size(), 5), // 조회된 데이터 수
                () -> assertEquals(users.getTotalElements(), 10), // 전체 데이터 수
                () -> assertEquals(users.getNumber(), 0), // 페이지 번호
                () -> assertEquals(users.getTotalPages(), 2), // 전체 페이지 번호
                () -> assertTrue(users.isFirst()), // 첫 번째 페이지 t/f
                () -> assertTrue(users.hasNext()) // 다음 페이지 t/f
        );
    }

    @Test
    @DisplayName("username을 활용하여 user를 조회하는 테스트")
    void findByUserName() {

        // given
        for (int i = 0; i < 10; i++) {
            User user = generateUser(i);
            userRepository.save(user);
        }

        // when
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<UserAdminMainResponseDto> users = userService.findByUserName(pageRequest, "user");

        // then
        assertAll(
                () -> assertEquals(users.getContent().size(), 5), // 조회된 데이터 수
                () -> assertEquals(users.getTotalElements(), 10), // 전체 데이터 수
                () -> assertEquals(users.getNumber(), 0), // 페이지 번호
                () -> assertEquals(users.getTotalPages(), 2), // 전체 페이지 번호
                () -> assertTrue(users.isFirst()), // 첫 번째 페이지 t/f
                () -> assertTrue(users.hasNext()) // 다음 페이지 t/f
        );
    }

    @Test
    @DisplayName("user의 role를 활용하여 user를 조회하는 테스트")
    void findByUserRole() {

        // given
        for (int i = 0; i < 10; i++) {
            User user = generateUser(i);
            userRepository.save(user);
        }

        // when
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<UserAdminMainResponseDto> users = userService.findByUserRole(pageRequest, Role.ROLE_USER);

        // then
        assertAll(
                () -> assertEquals(users.getContent().size(), 5), // 조회된 데이터 수
                () -> assertEquals(users.getTotalElements(), 10), // 전체 데이터 수
                () -> assertEquals(users.getNumber(), 0), // 페이지 번호
                () -> assertEquals(users.getTotalPages(), 2), // 전체 페이지 번호
                () -> assertTrue(users.isFirst()), // 첫 번째 페이지 t/f
                () -> assertTrue(users.hasNext()) // 다음 페이지 t/f
        );
    }

    private User generateUser(int index) {
        return User.builder()
                .email("user" + index + "@email.com")
                .name("user" + index)
                .userImageUrl("/img")
                .provider(Provider.SELF)
                .role(Role.ROLE_USER)
                .build();
    }

    @AfterEach
    void afterEach() {
        userRepository.deleteAll();
    }
}