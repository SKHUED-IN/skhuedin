package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.user.Provider;
import com.skhuedin.skhuedin.domain.user.Role;
import com.skhuedin.skhuedin.domain.user.User;
import com.skhuedin.skhuedin.dto.follow.FollowDeleteRequestDto;
import com.skhuedin.skhuedin.dto.follow.FollowMainResponseDto;
import com.skhuedin.skhuedin.dto.follow.FollowSaveRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@SpringBootTest
@Sql("/truncate.sql")
class FollowServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    FollowService followService;

    static User generateUser(String email, String name) {
        return User.builder()
                .email(email)
                .name(name)
                .provider(Provider.SELF)
                .userImageUrl("/images/user.png")
                .role(Role.ROLE_USER)
                .build();
    }

    static FollowSaveRequestDto generateFollowSaveRequestDto(Long fromUserId, Long toUserId) {
        return FollowSaveRequestDto.builder()
                .fromUserId(fromUserId)
                .toUserId(toUserId)
                .build();
    }

    @Test
    @DisplayName("FollowRequestDto를 활용하여 Follow 객체를 생성하여 저장하는 테스트 - 성공")
    void saveNewFollow() {

        // given
        User fromUser = generateUser("fromUser@email.com", "fromUser");
        User toUser = generateUser("toUser@email.com", "toUser");

        Long fromUserId = userService.save(fromUser);
        Long toUserId = userService.save(toUser);

        FollowSaveRequestDto requestDto = generateFollowSaveRequestDto(fromUserId, toUserId);

        // when
        Long followId = followService.save(requestDto);
        FollowMainResponseDto responseDto = followService.findById(followId);

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(fromUserId, responseDto.getFromUser().getId()),
                () -> Assertions.assertEquals(toUserId, responseDto.getToUser().getId())
        );
    }

    @Test
    @DisplayName("FollowRequestDto를 활용하여 Follow 객체를 n번 생성하여 저장하는 테스트 - 성공")
    void saveAgainFollow() {

        // given
        User fromUser = generateUser("fromUser@email.com", "fromUser");
        User toUser = generateUser("toUser@email.com", "toUser");

        Long fromUserId = userService.save(fromUser);
        Long toUserId = userService.save(toUser);

        FollowSaveRequestDto requestDto1 = generateFollowSaveRequestDto(fromUserId, toUserId);
        FollowSaveRequestDto requestDto2 = generateFollowSaveRequestDto(fromUserId, toUserId);

        // when
        Long follow1Id = followService.save(requestDto1);
        Long follow2Id = followService.save(requestDto2); // 기존에 동일한 id로 입력한다면 기존 것을 조회하여 반환

        // then
        Assertions.assertEquals(follow1Id, follow2Id);
    }

    @Test
    @DisplayName("follow id를 활용하여 FollowMainResponseDto를 조회하는 테스트 - 성공")
    void findById() {

        // given
        User fromUser = generateUser("fromUser@email.com", "fromUser");
        User toUser = generateUser("toUser@email.com", "toUser");

        Long fromUserId = userService.save(fromUser);
        Long toUserId = userService.save(toUser);

        FollowSaveRequestDto requestDto = generateFollowSaveRequestDto(fromUserId, toUserId);
        Long followId = followService.save(requestDto);

        // when
        FollowMainResponseDto responseDto = followService.findById(followId);

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(fromUserId, responseDto.getFromUser().getId()),
                () -> Assertions.assertEquals(toUserId, responseDto.getToUser().getId())
        );
    }

    @Test
    @DisplayName("존재하지 않는 follow id를 활용하여 FollowMainResponseDto를 조회하는 테스트 - 실패")
    void findByNotExistId() {

        // given & when & then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> followService.findById(0L)
        );
    }

    @Test
    @DisplayName("fromUser의 id를 활용하여 FollowMainResponseDto list를 조회하는 테스트 - 성공")
    void findByFromUserId() {

        // given
        User fromUser = generateUser("fromUser@email.com", "fromUser");
        User toUser1 = generateUser("toUser1@email.com", "toUser1");
        User toUser2 = generateUser("toUser2@email.com", "toUser2");

        Long fromUserId = userService.save(fromUser);
        Long toUser1Id = userService.save(toUser1);
        Long toUser2Id = userService.save(toUser2);

        FollowSaveRequestDto requestDto1 = generateFollowSaveRequestDto(fromUserId, toUser1Id);
        FollowSaveRequestDto requestDto2 = generateFollowSaveRequestDto(fromUserId, toUser2Id);
        followService.save(requestDto1);
        followService.save(requestDto2);

        // when
        List<FollowMainResponseDto> follows = followService.findByFromUserId(fromUserId);

        // then
        Assertions.assertEquals(2, follows.size());
    }

    @Test
    @DisplayName("toUser의 id를 활용하여 FollowMainResponseDto list를 조회하는 테스트 - 성공")
    void findByToUserId() {

        // given
        User fromUser1 = generateUser("fromUser1@email.com", "fromUser1");
        User fromUser2 = generateUser("fromUser2@email.com", "fromUser2");
        User toUser = generateUser("toUser@email.com", "toUser");

        Long fromUser1Id = userService.save(fromUser1);
        Long fromUser2Id = userService.save(fromUser2);
        Long toUserId = userService.save(toUser);

        FollowSaveRequestDto requestDto1 = generateFollowSaveRequestDto(fromUser1Id, toUserId);
        FollowSaveRequestDto requestDto2 = generateFollowSaveRequestDto(fromUser2Id, toUserId);

        followService.save(requestDto1);
        followService.save(requestDto2);

        // when
        List<FollowMainResponseDto> follows = followService.findByToUserId(toUserId);

        // then
        Assertions.assertEquals(2, follows.size());
    }

    @Test
    @DisplayName("FollowDeleteRequestDto를 활용하여 follow를 삭제하는 테스트 - 성공")
    void delete() {

        // given
        User fromUser = generateUser("fromUser@email.com", "fromUser");
        User toUser = generateUser("toUser@email.com", "toUser");

        Long fromUserId = userService.save(fromUser);
        Long toUserId = userService.save(toUser);

        FollowSaveRequestDto requestDto = generateFollowSaveRequestDto(fromUserId, toUserId);
        Long followId = followService.save(requestDto);

        // when
        FollowDeleteRequestDto followDeleteRequestDto = FollowDeleteRequestDto.builder()
                .fromUserId(fromUserId)
                .toUserId(toUserId)
                .build();

        followService.delete(followDeleteRequestDto);

        // then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> followService.findById(followId)
        );
    }

    @Test
    @DisplayName("존재하지 않는 user id를 가진 RequestDto를 활용하여 follow를 삭제하는 테스트 - 실패")
    void deleteWithNotExistUserId() {

        // given
        FollowDeleteRequestDto requestDto = FollowDeleteRequestDto.builder()
                .fromUserId(0L)
                .toUserId(0L)
                .build();

        // when & then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> followService.delete(requestDto)
        );
    }
}