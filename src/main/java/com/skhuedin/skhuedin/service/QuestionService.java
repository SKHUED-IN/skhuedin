package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.Question;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.dto.question.QuestionMainResponseDto;
import com.skhuedin.skhuedin.dto.question.QuestionSaveRequestDto;
import com.skhuedin.skhuedin.repository.QuestionRepository;
import com.skhuedin.skhuedin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long save(QuestionSaveRequestDto requestDto) {
        User targetUser = getUser(requestDto.getTargetUserId());
        User writerUser = getUser(requestDto.getWriterUserId());

        return questionRepository.save(requestDto.toEntity(targetUser, writerUser)).getId();
    }

    @Transactional
    public Long update(Long id, QuestionSaveRequestDto requestDto) {
        User targetUser = getUser(requestDto.getTargetUserId());
        User writerUser = getUser(requestDto.getWriterUserId());

        Question question = getQuestion(id);
        question.updateQuestion(requestDto.toEntity(targetUser, writerUser));

        return question.getId();
    }

    @Transactional
    public void delete(Long id) {
        Question question = getQuestion(id);
        questionRepository.delete(question);
    }

    public QuestionMainResponseDto findById(Long id) {
        Question question = getQuestion(id);
        return new QuestionMainResponseDto(question);
    }

    public List<QuestionMainResponseDto> findByTargetUserId(Long id) {
        User targetUser = getUser(id);
        List<Question> questions = questionRepository.findByTargetUserIdOrderByLastModifiedDateDesc(id);
        return questions.stream()
                .map(question -> new QuestionMainResponseDto(question))
                .collect(Collectors.toList());
    }

    @Transactional
    public void addView(Long id) {
        Question question = getQuestion(id);
        question.addView();
    }

    private Question getQuestion(Long id) {
        return questionRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 question 이 존재하지 않습니다. id=" + id));
    }

    private User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 user 가 존재하지 않습니다. id=" + id));
    }
}