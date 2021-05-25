package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.Category;
import com.skhuedin.skhuedin.dto.category.CategoryMainResponseDto;
import com.skhuedin.skhuedin.dto.category.CategoryRequestDto;
import com.skhuedin.skhuedin.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public void save(CategoryRequestDto requestDto) {

        categoryRepository.save(requestDto.toEntity());
    }

    public List<CategoryMainResponseDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(category -> new CategoryMainResponseDto(category)).collect(Collectors.toList());
    }
    @Transactional
    public void addWeight(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new IllegalArgumentException("해당 question 이 존재하지 않습니다. id=" + categoryId));
        category.addWeight();

    }
    @Transactional
    public void subtractWeight(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new IllegalArgumentException("해당 question 이 존재하지 않습니다. id=" + categoryId));
        category.subtractWeight();
        ;

    }
}
