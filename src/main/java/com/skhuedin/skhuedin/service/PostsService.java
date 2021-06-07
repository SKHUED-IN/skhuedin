package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.Blog;
import com.skhuedin.skhuedin.domain.Category;
import com.skhuedin.skhuedin.domain.Posts;
import com.skhuedin.skhuedin.dto.posts.PostsAdminMainResponseDto;
import com.skhuedin.skhuedin.dto.posts.PostsAdminResponseDto;
import com.skhuedin.skhuedin.dto.posts.PostsAdminUpdateRequestDto;
import com.skhuedin.skhuedin.dto.posts.PostsAdminUpdateResponseDto;
import com.skhuedin.skhuedin.dto.posts.PostsMainResponseDto;
import com.skhuedin.skhuedin.dto.posts.PostsSaveRequestDto;
import com.skhuedin.skhuedin.dto.posts.SuggestionsSaveRequestDto;
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
    public Long update(Long id, Long categoryId) {

        Posts posts = getPosts(id);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 category 입니다. id=" + id));
        posts.updateCategory(category);

        return posts.getId();
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = getPosts(id);
        postsRepository.delete(posts);
    }

    @Transactional
    public void deletePostAdmin(Long id) {
        Posts posts = getPosts(id);
        posts.setDeleteStatus();
    }

    public PostsMainResponseDto findById(Long id) {
        Posts posts = getPosts(id);
        return new PostsMainResponseDto(posts);
    }

    public List<PostsMainResponseDto> findByCategoryId(Long categoryId, Pageable pageable) {
        List<Posts> posts = postsRepository.findByCategoryIdOrderByView(categoryId, pageable);
        return posts.stream()
                .map(posts1 -> new PostsMainResponseDto(posts1))
                .collect(Collectors.toList());
    }

    public List<PostsAdminResponseDto> findAll() {
        return postsRepository.findAll().stream()
                .map(post -> new PostsAdminResponseDto(post))
                .collect(Collectors.toList());
    }

    public Long findByCategoryId(Long id) {
        List<Posts> posts = postsRepository.findPostsByCategoryId(id);
        Long count = Long.valueOf(posts.size());
        return count;
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
        Category category = categoryRepository.findByCategoryName("건의사항").orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 카테고리 입니다."));

        Blog blog = blogRepository.findByUserName("admin").orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 회원의 이름입니다."));
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

    public Page<PostsAdminMainResponseDto> findBySuggestions(Pageable pageable) {
        return postsRepository.findBySuggestions(pageable)
                .map(posts -> new PostsAdminMainResponseDto(posts));
    }

    /* private 메소드 */
    private Blog getBlog(Long id) {
        return blogRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 blog 입니다. id=" + id)
        );
    }

    private Posts getPosts(Long id) {
        return postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 posts 입니다. id=" + id)
        );
    }

    private Category getCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 category 입니다. id=" + id));
    }
}