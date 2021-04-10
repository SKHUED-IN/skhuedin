package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.Question;
import com.skhuedin.skhuedin.dto.question.QuestionMainResponseDto;
import com.skhuedin.skhuedin.dto.question.QuestionSaveRequestDto;
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
                .map(question -> new QuestionMainResponseDto(question))
                .collect(Collectors.toList());
    }
}