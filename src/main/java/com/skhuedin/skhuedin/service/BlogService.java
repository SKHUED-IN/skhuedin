package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.Blog;
import com.skhuedin.skhuedin.domain.UploadFile;
import com.skhuedin.skhuedin.domain.user.User;
import com.skhuedin.skhuedin.dto.blog.BlogAdminMainResponseDto;
import com.skhuedin.skhuedin.dto.blog.BlogMainResponseDto;
import com.skhuedin.skhuedin.dto.blog.BlogSaveRequestDto;
import com.skhuedin.skhuedin.dto.posts.PostsMainResponseDto;
import com.skhuedin.skhuedin.file.FileStore;
import com.skhuedin.skhuedin.repository.BlogRepository;
import com.skhuedin.skhuedin.repository.PostsRepository;
import com.skhuedin.skhuedin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class BlogService {

    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final PostsRepository postsRepository;
    private final FileStore fileStore;

    @Transactional
    public Long save(BlogSaveRequestDto requestDto, MultipartFile file) throws IOException {
        UploadFile uploadFile = fileStore.storeFile(file);

        User user = getUser(requestDto.getUserId());
        Blog blog = requestDto.toEntity(user, uploadFile);

        return blogRepository.save(blog).getId();
    }

    @Transactional
    public Long update(BlogSaveRequestDto updateDto, MultipartFile file) throws IOException {

        User user = getUser(updateDto.getUserId());
        Blog blog = user.getBlog();

        if (blog.getUploadFile() != null) {
            fileStore.removeFile(blog.getUploadFile().getStoreFileName());
        }
        UploadFile uploadFile = fileStore.storeFile(file);

        Blog updateBlog = updateDto.toEntity(user, uploadFile);
        blog.updateBlog(updateBlog);

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

    public BlogMainResponseDto findById(Long id, Pageable pageable) {
        Blog blog = getBlog(id);
        Page<PostsMainResponseDto> postsPage = postsRepository.findByBlogId(blog.getId(), false, pageable)
                .map(posts -> new PostsMainResponseDto(posts));

        return new BlogMainResponseDto(blog, postsPage);
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

    public BlogMainResponseDto findByUserId(Long userId) {

        Blog blog = blogRepository.findByUserId(userId).orElseThrow(() ->
                new IllegalArgumentException("blog가 존재하지 않는 user 입니다."));

        return new BlogMainResponseDto(blog);
    }

    /* admin 전용 */
    public Page<BlogAdminMainResponseDto> findAllForAdmin(Pageable pageable) {
        return blogRepository.findAll(pageable)
                .map(blog -> new BlogAdminMainResponseDto(blog));
    }

    /* private 메소드 */
    private Blog getBlog(Long id) {
        return blogRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 blog 가 존재하지 않습니다. id=" + id));
    }

    private User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 user 가 존재하지 않습니다. id=" + id));
    }
}