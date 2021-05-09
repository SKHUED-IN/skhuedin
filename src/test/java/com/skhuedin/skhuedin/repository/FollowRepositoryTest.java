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

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
                .graduationYear(LocalDateTime.now())
                .entranceYear(LocalDateTime.now())
                .provider(Provider.SELF)
                .build();

        fromUser1 = User.builder()
                .email("target@email.com")
                .password("1234")
                .name("fromUser1")
                .userImageUrl("/img")
                .graduationYear(LocalDateTime.now())
                .entranceYear(LocalDateTime.now())
                .provider(Provider.SELF)
                .build();

        fromUser2 = User.builder()
                .email("target@email.com")
                .password("1234")
                .name("fromUser2")
                .userImageUrl("/img")
                .graduationYear(LocalDateTime.now())
                .entranceYear(LocalDateTime.now())
                .provider(Provider.SELF)
                .build();

        fromUser3 = User.builder()
                .email("target@email.com")
                .password("1234")
                .name("fromUser3")
                .userImageUrl("/img")
                .graduationYear(LocalDateTime.now())
                .entranceYear(LocalDateTime.now())
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
}