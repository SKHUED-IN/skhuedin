package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.Follow;
import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.domain.user.Role;
import com.skhuedin.skhuedin.domain.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@DataJpaTest
@Sql("/truncate.sql")
class FollowRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    static User generateUser(String email, String name) {
        return User.builder()
                .email(email)
                .name(name)
                .provider(Provider.SELF)
                .userImageUrl("/images/user.png")
                .role(Role.ROLE_USER)
                .build();
    }

    static Follow generateFollow(User fromUser, User toUser) {
        return Follow.builder()
                .fromUser(fromUser)
                .toUser(toUser)
                .build();
    }

    @Test
    @DisplayName("toUser 의 id를 활용하여 조회하는 테스트 - 성공")
    void findByToUserId() {

        // given
        User fromUser1 = generateUser("fromUser1@email.com", "fromUser1");
        User fromUser2 = generateUser("fromUser2@email.com", "fromUser2");
        User toUser = generateUser("toUser@email.com", "toUser");

        fromUser1 = userRepository.save(fromUser1);
        fromUser2 = userRepository.save(fromUser2);
        toUser = userRepository.save(toUser);

        Follow follow1 = generateFollow(fromUser1, toUser);
        Follow follow2 = generateFollow(fromUser2, toUser);

        followRepository.save(follow1);
        followRepository.save(follow2);

        // when
        List<Follow> follows = followRepository.findByToUserId(toUser.getId());

        // then
        Assertions.assertEquals(2, follows.size());
    }

    @Test
    @DisplayName("fromUser의 id를 활용하여 조회하는 테스트 - 성공")
    void findByFromUserId() {

        // given
        User fromUser = generateUser("fromUser@email.com", "fromUser");
        User toUser1 = generateUser("toUser1@email.com", "toUser1");
        User toUser2 = generateUser("toUser2@email.com", "toUser2");

        fromUser = userRepository.save(fromUser);
        toUser1 = userRepository.save(toUser1);
        toUser2 = userRepository.save(toUser2);

        Follow follow1 = generateFollow(fromUser, toUser1);
        Follow follow2 = generateFollow(fromUser, toUser2);

        followRepository.save(follow1);
        followRepository.save(follow2);

        // when
        List<Follow> follows = followRepository.findByFromUserId(fromUser.getId());

        // then
        Assertions.assertEquals(2, follows.size());
    }

    @Test
    @DisplayName("fromUser와 toUser id를 활용하여 조회하는 테스트 - 성공")
    void findByFromUserIdAndToUserId() {

        // given
        User fromUser = generateUser("fromUser@email.com", "fromUser");
        User toUser = generateUser("toUser@email.com", "toUser");

        userRepository.save(fromUser);
        userRepository.save(toUser);

        Follow follow = generateFollow(fromUser, toUser);

        followRepository.save(follow);

        // when
        Follow findFollow = followRepository.findByFromUserIdAndToUserId(fromUser.getId(), toUser.getId()).get();

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(fromUser, findFollow.getFromUser()),
                () -> Assertions.assertEquals(toUser, findFollow.getToUser())
        );
    }
}