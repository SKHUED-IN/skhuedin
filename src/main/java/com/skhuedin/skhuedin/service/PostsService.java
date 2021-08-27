package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.blog.Blog;
import com.skhuedin.skhuedin.domain.category.Category;
import com.skhuedin.skhuedin.domain.posts.Posts;
import com.skhuedin.skhuedin.dto.posts.PostsAdminMainResponseDto;
import com.skhuedin.skhuedin.dto.posts.PostsAdminUpdateRequestDto;
import com.skhuedin.skhuedin.dto.posts.PostsAdminUpdateResponseDto;
import com.skhuedin.skhuedin.dto.posts.PostsMainResponseDto;
import com.skhuedin.skhuedin.dto.posts.PostsSaveRequestDto;
import com.skhuedin.skhuedin.dto.posts.SuggestionsSaveRequestDto;
import com.skhuedin.skhuedin.error.exception.EntityNotFoundException;
import com.skhuedin.skhuedin.repository.BlogRepository;
import com.skhuedin.skhuedin.repository.CategoryRepository;
import com.skhuedin.skhuedin.repository.PostsRepository;
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
public class PostsService {

    private final BlogRepository blogRepository;
    private final PostsRepository postsRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        Blog blog = getBlog(requestDto.getBlogId());

        if (blog.getPosts().size() == 0) {
            Posts posts = postsRepository.save(requestDto.toEntity(blog));
            Category category = getCategory("자기소개");

            posts.updateCategory(category);
            return posts.getId();
        } else if (blog.getPosts().size() == 1) {
            Posts posts = postsRepository.save(requestDto.toEntity(blog));
            Category category = getCategory("학교생활");

            posts.updateCategory(category);
            return posts.getId();
        } else if (blog.getPosts().size() == 2) {
            Posts posts = postsRepository.save(requestDto.toEntity(blog));
            Category category = getCategory("졸업 후 현재");

            posts.updateCategory(category);
            return posts.getId();
        }

        return postsRepository.save(requestDto.toEntity(blog)).getId();
    }

    @Transactional
    public Long update(Long id, PostsSaveRequestDto requestDto) {
        Blog blog = getBlog(requestDto.getBlogId());

        Posts posts = getPosts(id);
        posts.updatePost(requestDto.toEntity(blog));

        return posts.getId();
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = getPosts(id);
        postsRepository.delete(posts);
    }

    public PostsMainResponseDto findById(Long id) {
        Posts posts = getPosts(id);
        return new PostsMainResponseDto(posts);
    }

    public List<PostsMainResponseDto> findByCategoryId(Long categoryId, Pageable pageable) {
        return postsRepository.findByCategoryIdOrderByView(categoryId, pageable)
                .stream()
                .map(posts -> new PostsMainResponseDto(posts))
                .collect(Collectors.toList());
    }

    public Long countByCategoryId(Long id) {
        return postsRepository.countByCategoryId(id);
    }

    public Page<PostsMainResponseDto> findByBlogId(Long blogId, Pageable pageable) {
        // 삭제 상태 default status가 false인 것만 조회한다. -> 일반적인 user 입장
        return postsRepository.findByBlogId(blogId, false, pageable)
                .map(posts -> new PostsMainResponseDto(posts));
    }

    @Transactional
    public void addView(Long id) {
        Posts posts = getPosts(id);
        posts.addView();
    }

    @Transactional
    public Long saveSuggestions(SuggestionsSaveRequestDto requestDto) {
        Category category = categoryRepository.findByCategoryName("건의사항").orElseThrow(EntityNotFoundException::new);

        Blog blog = blogRepository.findByUserEmail("admin@email.com").orElseThrow(EntityNotFoundException::new);

        return postsRepository.save(requestDto.toEntity(blog, category)).getId();
    }

    /* admin 전용 */
    public Page<PostsAdminMainResponseDto> findAll(Pageable pageable) {
        return postsRepository.findAll(pageable)
                .map(posts -> new PostsAdminMainResponseDto(posts));
    }

    public Page<PostsAdminMainResponseDto> findByUserName(Pageable pageable, String username) {
        return postsRepository.findByUserName(pageable, username)
                .map(posts -> new PostsAdminMainResponseDto(posts));
    }

    public Page<PostsAdminMainResponseDto> findByCategoryName(Pageable pageable, String CategoryName) {
        return postsRepository.findByCategoryName(pageable, CategoryName)
                .map(posts -> new PostsAdminMainResponseDto(posts));
    }

    public PostsAdminUpdateResponseDto findByIdByAdmin(Long id) {
        return new PostsAdminUpdateResponseDto(getPosts(id));
    }

    @Transactional
    public void update(PostsAdminUpdateRequestDto requestDto) {
        Posts posts = getPosts(requestDto.getId());
        Category category = getCategory(requestDto.getCategoryId());

        posts.updateCategoryAndStatus(category, requestDto.getDeleteStatus());
    }

    public Page<PostsAdminMainResponseDto> findSuggestions(Pageable pageable) {
        return postsRepository.findSuggestions(pageable)
                .map(posts -> new PostsAdminMainResponseDto(posts));
    }

    /* private 메소드 */
    private Blog getBlog(Long id) {
        return blogRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    private Posts getPosts(Long id) {
        return postsRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    private Category getCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    private Category getCategory(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName).orElseThrow(EntityNotFoundException::new);
    }
}