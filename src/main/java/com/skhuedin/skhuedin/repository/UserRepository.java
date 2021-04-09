package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserRepository {

    @PersistenceContext
    EntityManager em;

    public Long save(User user) {
        em.persist(user);
        return user.getId();
    }

//    public User findById(Long id) {
//        return em.find(User.class, id);
//    }
//
//    public User findByEmail(String email) {
//        return em.find(User.class, email);
//    }

    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }
}
