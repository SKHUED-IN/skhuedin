package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.Blog;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.dto.blog.BlogMainResponseDto;
import com.skhuedin.skhuedin.dto.blog.BlogSaveRequestDto;
import com.skhuedin.skhuedin.repository.BlogRepository;
import com.skhuedin.skhuedin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BlogService {

    private final UserRepository userRepository;
    private final BlogRepository blogRepository;

    @Transactional
    public Long save(BlogSaveRequestDto requestDto) {
        User user = getUser(requestDto.getUserId());

        return blogRepository.save(requestDto.toEntity(user)).getId();
    }

    @Transactional
    public Long update(Long id, BlogSaveRequestDto requestDto) {
        User user = getUser(requestDto.getUserId());

        Blog blog = getBlog(id);
        blog.updateBlog(requestDto.toEntity(user));

        return blog.getId();
    }

    @Transactional
    public void delete(Long id) {
        Blog blog = getBlog(id);
        blogRepository.delete(blog);
    }

    public BlogMainResponseDto findById(Long id) {
        Blog blog = getBlog(id);

        return new BlogMainResponseDto(blog);
    }

    public List<BlogMainResponseDto> findAll() {
        return blogRepository.findAll().stream()
                .map(blog -> new BlogMainResponseDto(blog))
                .collect(Collectors.toList());
    }

    private Blog getBlog(Long id) {
        return blogRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 blog 가 존재하지 않습니다. id=" + id));
    }

    private User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 user 가 존재하지 않습니다. id=" + id));
    }
}