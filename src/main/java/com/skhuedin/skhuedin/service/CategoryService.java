package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.Category;
import com.skhuedin.skhuedin.dto.category.CategoryMainResponseDto;
import com.skhuedin.skhuedin.dto.category.CategoryRequestDto;
import com.skhuedin.skhuedin.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final PostsService postsService;

    @Transactional
    public Long save(CategoryRequestDto requestDto) {
        return categoryRepository.save(requestDto.toEntity()).getId();
    }

    public List<CategoryMainResponseDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(category -> new CategoryMainResponseDto(category))
                .collect(Collectors.toList());
    }

    public Page<CategoryMainResponseDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(category -> changeMainResponseDto(category.getId()));
    }

    public CategoryMainResponseDto findById(Long id) {
        return new CategoryMainResponseDto(getCategory(id));
    }

    public List<CategoryMainResponseDto> findByWeight() {
        return categoryRepository.findByWeight()
                .stream()
                .map(category -> changeMainResponseDto(category.getId()))
                .collect(Collectors.toList());
    }

    public Page<CategoryMainResponseDto> findByWeightPage(Pageable pageable) {
        return categoryRepository.findByWeightPage(pageable)
                .map(category -> changeMainResponseDto(category.getId()));
    }

    public Page<CategoryMainResponseDto> findByCreatedDate(Pageable pageable) {
        return categoryRepository.findByCreatedDate(pageable)
                .map(category -> changeMainResponseDto(category.getId()));
    }

    @Transactional
    public void delete(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    @Transactional
    public void addWeight(Long categoryId) {
        Category category = getCategory(categoryId);
        category.addWeight();
    }

    @Transactional
    public void subtractWeight(Long categoryId) {
        Category category = getCategory(categoryId);
        category.subtractWeight();
    }

    public CategoryMainResponseDto findByIdByAdmin(Long id) {
        return changeMainResponseDto(id);
    }

    /* private 메소드 */
    private CategoryMainResponseDto changeMainResponseDto(Long categoryId) {
        CategoryMainResponseDto responseDto = new CategoryMainResponseDto(getCategory(categoryId));
        responseDto.addReferPostCount(postsService.countByCategoryId(categoryId));
        return responseDto;
    }

    private Category getCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 question 이 존재하지 않습니다. id=" + id));
    }
}
