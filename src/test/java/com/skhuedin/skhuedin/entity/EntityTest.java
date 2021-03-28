package com.skhuedin.skhuedin.entity;


import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.domain.User;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@Rollback(value = false)
@SpringBootTest
public class EntityTest {
    @PersistenceContext
    EntityManager em;


    @Test
    public void User생성() {
        User user1 = User.builder()
                .email("hello@gmail.com")
                .password("1234")
                .provider(Provider.kakao)
                .userImageUrl("수닿방구")
                .build();
        em.persist(user1);

        em.flush(); em.clear();

        User findUser = em.find(User.class, 1L);
        assertThat(findUser.getEmail()).isEqualTo(user1.getEmail());
    }

    @Test
    public void User생성_실패() {
        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalArgumentException.class, ()->{
                    User user1 = User.builder()
                            .password("1234")
                            .provider(Provider.kakao)
                            .userImageUrl("수닿방구")
                            .build();

                    em.persist(user1);
                });
    }





}
