package com.skhuedin.skhuedin.service;


import com.skhuedin.skhuedin.domain.Question;
import com.skhuedin.skhuedin.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Transactional
    public Long join(Question question) {
        return questionRepository.save(question).getId();
    }


}
