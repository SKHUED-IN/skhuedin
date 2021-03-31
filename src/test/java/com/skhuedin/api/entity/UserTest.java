package com.skhuedin.api.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.UnexpectedRollbackException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
@Rollback(value = false)
class UserTest {

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("User_생성")
    public void userCreate() {
        User user = createUser();
        User findUser = em.find(User.class, 1L);
        assertThat(findUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("User_생성_실패")
    public void userCreateFail() {
        Assertions.assertThrows(
                IllegalArgumentException.class, () -> {
                    User user1 = User.builder()
                            .password("1234")
                            .provider(Provider.KAKAO)
                            .userImageUrl("수닿방구")
                            .build();
                    em.persist(user1);
                });
    }

    @Test
    @DisplayName("User_로그인성공")
    public void login() {
        User user = createUser();
        User findUser1 = em.createQuery("select u from User u where u.email = :email", User.class)
                .setParameter("email", user.getEmail()).getSingleResult();
        if (findUser1 != null) {
            assertThat(findUser1.getEmail()).isEqualTo(user.getEmail());
        }
    }

    @Test
    @DisplayName("User_로그인실패")
    public void login_fail() {
        User user = createUser();
        User findUser1 = em.createQuery("select u from User u where u.email = :email", User.class)
                .setParameter("email", user.getEmail()).getSingleResult();

        org.junit.jupiter.api.Assertions.assertThrows(NoResultException.class, () -> {
            User findUser2 = em.createQuery("select u from User u where u.email = :email", User.class)
                    .setParameter("email", "hello@gmail.com").getSingleResult();
        });
    }

    @Test
    @DisplayName("단건 조회 ")
    public void findOne() {
        List<User> users = createUsers();
        for (User user : users) {
            em.persist(user);
        }
        User findUser = users.get((int) (Math.random() * 29 + 1));
        User findDbUser = em.find(User.class, findUser.getId());
        assertThat(findUser.getEmail()).isEqualTo(findDbUser.getEmail());

    }

    @Test
    @DisplayName("단건 조회 실패")
    public void findOneFail() {
        List<User> users = createUsers();
        for (User user : users) {
            em.persist(user);
        }
        User findUser = users.get((int) (Math.random() * 29 + 1));
        User findDbUser = em.find(User.class, findUser.getId() / 2);
        assertThat(findUser.getEmail()).isNotEqualTo(findDbUser.getEmail());
    }

    @Test
    @DisplayName("이메일 수정")
    public void updateEmail() {
        User user = createUser();
        User findUser = em.find(User.class, 1L);
        findUser.changeEmail("이메일 내용을 수정했습니다.");
        User findUser2 = em.find(User.class, 1L);

        assertThat(findUser.getEmail()).isEqualTo(findUser2.getEmail());
    }

    @Test
    @DisplayName("유저 삭제")
    public void deleteUser() {
        User user1 = User.builder()
                .email("google.kakao.naver")
                .password("test123")
                .provider(Provider.GOOGLE)
                .userImageUrl("photo.asnvsd")
                .build();
        em.persist(user1);

        User user2 = User.builder()
                .email("google.kakao.naver")
                .password("test123")
                .provider(Provider.GOOGLE)
                .userImageUrl("photo.asnvsd")
                .build();
        em.persist(user2);

        em.remove(user1);
        assertThat(em.find(User.class, user1.getId())).isNull();
    }

    User createUser() {
        User user = User.builder()
                .email("google.kakao.naver")
                .password("test123")
                .provider(Provider.GOOGLE)
                .userImageUrl("photo.asnvsd")
                .build();
        em.persist(user);
        em.flush();
        em.clear();

        return user;
    }

    List<User> createUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user1 = User.builder()
                    .email("abc" + i + "@gmail.com")
                    .password((Math.random() * 9000 + 1) + Integer.toString(i))
                    .provider(Provider.SELF)
                    .userImageUrl("www.google.com")
                    .build();
            users.add(user1);

            User user2 = User.builder()
                    .email("def" + i + "@naver.com")
                    .password((Math.random() * 9000 + 1) + Integer.toString(i))
                    .provider(Provider.GOOGLE)
                    .userImageUrl("www.naver.com")
                    .build();
            users.add(user2);

            User user3 = User.builder()
                    .email("ghi" + i + "@github.com")
                    .password((Math.random() * 9000 + 1) + Integer.toString(i))
                    .provider(Provider.KAKAO)
                    .userImageUrl("www.github.com")
                    .build();
            users.add(user3);
        }
        return users;
    }
}