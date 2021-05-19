package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.Blog;
import com.skhuedin.skhuedin.domain.File;
import com.skhuedin.skhuedin.domain.Posts;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.dto.blog.BlogMainResponseDto;
import com.skhuedin.skhuedin.dto.blog.BlogSaveRequestDto;
import com.skhuedin.skhuedin.dto.posts.PostsMainResponseDto;
import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;
import com.skhuedin.skhuedin.repository.BlogRepository;
import com.skhuedin.skhuedin.repository.FileRepository;
import com.skhuedin.skhuedin.repository.PostsRepository;
import com.skhuedin.skhuedin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class BlogService {

    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final PostsRepository postsRepository;
    private final FileRepository fileRepository;

    @Transactional
    public Long save(BlogSaveRequestDto requestDto, Long fileId) {
        User user = getUser(requestDto.getUserId());
        File file = getFile(fileId);

        return blogRepository.save(requestDto.toEntity(user, file)).getId();
    }

    @Transactional
    public Long update(Long id, BlogSaveRequestDto requestDto, Long fileId) {
        User user = getUser(requestDto.getUserId());
        Blog blog = getBlog(id);

        if (blog.getProfile().getId() != 1L) { // default image를 제외한 나머지 이미지는 update가 이뤄지면 삭제
            File removeFile = getFile(blog.getProfile().getId());
            java.io.File file = new java.io.File(removeFile.getPath() + "/" + removeFile.getName());
            if (file.delete()) {
                log.info(blog.getId() + " 기존 profile 삭제 성공");
            } else {
                log.info(blog.getId() +  "기존 profile 삭제 실패");
            }
            fileRepository.deleteById(blog.getProfile().getId()); // DB에서 삭제
        }

        File file = getFile(fileId);
        blog.updateBlog(requestDto.toEntity(user, file));

        return blog.getId();
    }

    @Transactional
    public void delete(Long id) {
        Blog blog = getBlog(id);
        blogRepository.delete(blog);
    }

    public BlogMainResponseDto findById(Long id) {
        Blog blog = getBlog(id);
        List<Posts> posts = postsRepository.findByBlogIdOrderByLastModifiedDateDesc(blog.getId());
        List<PostsMainResponseDto> collect = posts.stream()
                .map(PostsMainResponseDto::new)
                .collect(Collectors.toList());

        return new BlogMainResponseDto(blog, collect);
    }

    public Page<BlogMainResponseDto> findAll(Pageable pageable) {
        return blogRepository.findAll(pageable)
                .map(blog -> new BlogMainResponseDto(blog));
    }

    public Page<BlogMainResponseDto> findAllOrderByPostsView(Pageable pageable) {
        return blogRepository.findAllOrderByPostsView(pageable)
                .map(blog -> new BlogMainResponseDto(blog));
    }

    public Boolean existsByUserId(Long userId) {
        return blogRepository.existsByUserId(userId);
    }

    private Blog getBlog(Long id) {
        return blogRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 blog 가 존재하지 않습니다. id=" + id));
    }

    private User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 user 가 존재하지 않습니다. id=" + id));
    }

    private File getFile(Long fileId) {
        return fileRepository.findById(fileId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 file id 입니다."));
    }
}