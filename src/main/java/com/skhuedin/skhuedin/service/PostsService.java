package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.Blog;
import com.skhuedin.skhuedin.domain.Posts;
import com.skhuedin.skhuedin.dto.posts.PostsMainResponseDto;
import com.skhuedin.skhuedin.dto.posts.PostsSaveRequestDto;
import com.skhuedin.skhuedin.repository.BlogRepository;
import com.skhuedin.skhuedin.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
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
    public void delete(Long id) {
        Posts posts = getPosts(id);
        postsRepository.delete(posts);
    }

    public PostsMainResponseDto findById(Long id) {
        Posts posts = getPosts(id);
        return new PostsMainResponseDto(posts);
    }

    public List<PostsMainResponseDto> findByBlogId(Long blogId) {
        List<Posts> posts = postsRepository.findByBlogIdOrderByLastModifiedDateDesc(blogId);
        return posts.stream()
                .map(posts1 -> new PostsMainResponseDto(posts1))
                .collect(Collectors.toList());
    }

    @Transactional
    public void addView(Long id) {
        Posts posts = getPosts(id);
        posts.addView();
    }

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
}