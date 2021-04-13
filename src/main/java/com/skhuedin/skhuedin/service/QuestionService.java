package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.Question;
import com.skhuedin.skhuedin.dto.comment.CommentMainResponseDto;
import com.skhuedin.skhuedin.dto.question.QuestionMainResponseDto;
import com.skhuedin.skhuedin.dto.question.QuestionSaveRequestDto;
import com.skhuedin.skhuedin.repository.CommentRepository;
import com.skhuedin.skhuedin.repository.QuestionRepository;
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
    private final CommentRepository commentRepository;

    @Transactional
    public Long save(QuestionSaveRequestDto dto) {
        return questionRepository.save(dto.toEntity()).getId();
    }

    public Question findById(Long id) {
        return questionRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("question 을 찾을 수 없습니다."));
    }

    public List<QuestionMainResponseDto> findByTargetUserIdDesc(Long id) {
        return questionRepository.findByTargetUserIdOrderByLastModifiedDateDesc(id)
                .stream()
                .map(question -> {
                    List<CommentMainResponseDto> comments = commentRepository.findByQuestionId(question.getId())
                            .stream()
                            .map(comment -> new CommentMainResponseDto(comment))
                            .collect(Collectors.toList());

                    return new QuestionMainResponseDto(question, comments);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public Long update(Long id, QuestionSaveRequestDto dto) {
        Question question = questionRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("question 이 존재하지 않습니다."));

        question.updateQuestion(dto.toEntity());

        return question.getId();
    }

    @Transactional
    public void delete(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("question 이 존재하지 않습니다."));

        questionRepository.delete(question);
    }

    @Transactional
    public void addView(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("question 을 찾을 수 없습니다."));
        question.addView();
    }
}