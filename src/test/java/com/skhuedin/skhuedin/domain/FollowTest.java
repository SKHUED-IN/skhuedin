package com.skhuedin.skhuedin.domain;

import com.skhuedin.skhuedin.domain.user.Role;
import com.skhuedin.skhuedin.domain.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FollowTest {

    static User generateUser(String email, String name) {
        return User.builder()
                .email(email)
                .name(name)
                .provider(Provider.SELF)
                .userImageUrl("/images/user.png")
                .role(Role.ROLE_USER)
                .build();
    }

    @Test
    @DisplayName("Builder를 활용하여 Follow 객체를 생성하는 테스트 - 성공")
    void createByBuilder() {

        // given
        User fromUser = generateUser("from@email.com", "from");
        User toUser = generateUser("to@email.com", "to");

        // when
        Follow follow = Follow.builder()
                .fromUser(fromUser)
                .toUser(toUser)
                .build();

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(fromUser, follow.getFromUser()),
                () -> Assertions.assertEquals(toUser, follow.getToUser())
        );
    }
}