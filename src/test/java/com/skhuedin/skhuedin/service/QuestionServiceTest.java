package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.Question;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;




@Transactional
@Rollback(value = false)
@SpringBootTest
class QuestionServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionService questionService;


    @Test
    public void testQ() {
        User user1 = userRepository.findById(1L).get();
        User user2 = userRepository.findById(2L).get();

        Question question = new Question(null, "짛문있습니다.", "외래키 설정 이렇게 하는거 맞나요?", user1, user2);
        questionService.join(question);


    }

}