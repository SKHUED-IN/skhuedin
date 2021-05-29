package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.Category;
import com.skhuedin.skhuedin.domain.Posts;
import com.skhuedin.skhuedin.dto.category.CategoryMainResponseDto;
import com.skhuedin.skhuedin.dto.category.CategoryRequestDto;
import com.skhuedin.skhuedin.dto.posts.PostsMainResponseDto;
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
    public Long save(CategoryRequestDto requestDto) {
        return categoryRepository.save(requestDto.toEntity()).getId();
    }

    public List<CategoryMainResponseDto> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> new CategoryMainResponseDto(category))
                .collect(Collectors.toList());
    }

    public List<CategoryMainResponseDto> findByWight() {
        return categoryRepository.findByWeight()
                .stream()
                .map(category -> new CategoryMainResponseDto(category))
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long categoryId) {
        categoryRepository.deleteById(categoryId);
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
    }

    public CategoryMainResponseDto findById(Long id) {
        Category category = getCategory(id);
        return new CategoryMainResponseDto(category);
    }

    private Category getCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 category 입니다. id=" + id)
        );
    }
}
