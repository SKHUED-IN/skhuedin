package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.Follow;
import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Sql("/truncate.sql")
@Transactional
class FollowRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FollowRepository followRepository;

    User toUser;
    User fromUser1;
    User fromUser2;
    User fromUser3;

    @BeforeEach
    void beforeEach() {
        toUser = User.builder()
                .email("target@email.com")
                .password("1234")
                .name("toUser")
                .userImageUrl("/img")
                .graduationYear("2016")
                .entranceYear("2022")
                .provider(Provider.SELF)
                .build();

        fromUser1 = User.builder()
                .email("target@email.com")
                .password("1234")
                .name("fromUser1")
                .userImageUrl("/img")
                .graduationYear("2016")
                .entranceYear("2022")
                .provider(Provider.SELF)
                .build();

        fromUser2 = User.builder()
                .email("target@email.com")
                .password("1234")
                .name("fromUser2")
                .userImageUrl("/img")
                .graduationYear("2016")
                .entranceYear("2022")
                .provider(Provider.SELF)
                .build();

        fromUser3 = User.builder()
                .email("target@email.com")
                .password("1234")
                .name("fromUser3")
                .userImageUrl("/img")
                .graduationYear("2016")
                .entranceYear("2022")
                .provider(Provider.SELF)
                .build();

        userRepository.save(toUser);
        userRepository.save(fromUser1);
        userRepository.save(fromUser2);
        userRepository.save(fromUser3);
    }

    @Test
    @DisplayName("fromUser 3명이 toUser로 follow 하여 toUser로 조회하는 테스트")
    void findByToUserId() {

        // given
        Follow follow1 = Follow.builder()
                .toUser(toUser)
                .fromUser(fromUser1)
                .build();

        Follow follow2 = Follow.builder()
                .toUser(toUser)
                .fromUser(fromUser2)
                .build();

        Follow follow3 = Follow.builder()
                .toUser(toUser)
                .fromUser(fromUser3)
                .build();

        followRepository.save(follow1);
        followRepository.save(follow2);
        followRepository.save(follow3);

        // when
        List<Follow> follows = followRepository.findByToUserId(toUser.getId());

        // then
        assertEquals(follows.size(), 3);
    }

    @Test
    @DisplayName("fromUser 가 follow 한 toUser 목록을 조회하는 테스트")
    void findByFromUserId() {

        // given
        Follow follow = Follow.builder()
                .toUser(toUser)
                .fromUser(fromUser1)
                .build();

        followRepository.save(follow);

        // when
        List<Follow> follows = followRepository.findByFromUserId(follow.getFromUser().getId());

        // then
        assertEquals(1, follows.size());
    }

    @Test
    @DisplayName("toUserId 와 fromUserId 로 follow 를 조회하는 테스트")
    void findByToUserIdAndFromUserId() {

        // given
        Follow follow = Follow.builder()
                .toUser(toUser)
                .fromUser(fromUser1)
                .build();

        followRepository.save(follow);

        // when
        Follow findFollow = followRepository.findByToUserIdAndFromUserId(
                follow.getToUser().getId(),
                follow.getFromUser().getId());

        // then
        assertEquals(follow, findFollow);
    }

    @Test
    @DisplayName("fromUser 가 toUser 의 follow 유무를 확인하는 테스트")
    void existsByToUserIdAndFromUserId() {

        // given
        Follow follow = Follow.builder()
                .toUser(toUser)
                .fromUser(fromUser1)
                .build();

        Follow save = followRepository.save(follow);

        // when
        Boolean exists = followRepository.existsByToUserIdAndFromUserId(
                follow.getToUser().getId(),
                follow.getFromUser().getId());

        // then
        assertEquals(true, exists);
    }
}