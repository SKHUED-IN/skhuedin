package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.blog.Blog;
import com.skhuedin.skhuedin.domain.comment.Comment;
import com.skhuedin.skhuedin.domain.posts.Posts;
import com.skhuedin.skhuedin.domain.question.Question;
import com.skhuedin.skhuedin.domain.user.Role;
import com.skhuedin.skhuedin.domain.user.User;
import com.skhuedin.skhuedin.dto.user.UserAdminMainResponseDto;
import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;
import com.skhuedin.skhuedin.dto.user.UserUpdateDto;
import com.skhuedin.skhuedin.error.exception.EntityNotFoundException;
import com.skhuedin.skhuedin.repository.BlogRepository;
import com.skhuedin.skhuedin.repository.CommentRepository;
import com.skhuedin.skhuedin.repository.PostsRepository;
import com.skhuedin.skhuedin.repository.QuestionRepository;
import com.skhuedin.skhuedin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final PostsRepository postsRepository;
    private final CommentRepository commentRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    public Long save(User user) {
        return userRepository.save(user).getId();
    }

    @Transactional
    public void updateYearData(Long id, UserUpdateDto updateDto) {
        User user = getUser(id);
        user.addYear(updateDto.getEntranceYear(), updateDto.getGraduationYear());
    }

    @Transactional
    public void updateRole(Long id, Role role) {
        User user = getUser(id);
        user.updateRole(role);
    }

    @Transactional
    public void delete(Long id) {
        User findUser = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        Optional<Blog> blogOptional = blogRepository.findByUserId(id);
        List<Comment> comments = commentRepository.findByWriterUserId(id);
        List<Question> questions = questionRepository.findByWriterUserId(id);
        List<Question> targetQuestions = questionRepository.findByTargetUserId(id);

        deleteComments(comments);
        deleteQuestions(questions);
        deleteQuestions(targetQuestions);

        blogOptional.ifPresent((blog) -> {
            List<Posts> posts;
            posts = postsRepository.findByBlogId(blog.getId());
            for (Posts post : posts) {
                postsRepository.deleteById(post.getId());
            }
            blogRepository.delete(blog);
        });
        userRepository.delete(findUser);
    }

    private void deleteComments(List<Comment> comments) {
        if (!comments.isEmpty()) {
            for (Comment comment : comments) {
                commentRepository.delete(comment);
            }
        }
    }

    private void deleteQuestions(List<Question> questions) {
        if (!questions.isEmpty()) {
            for (Question question : questions) {
                List<Comment> innerComment = commentRepository.findByQuestionId(question.getId());

                deleteComments(innerComment);
                questionRepository.delete(question);
            }
        }
    }

    public UserMainResponseDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return new UserMainResponseDto(user);
    }

    public List<UserMainResponseDto> findAll() {
        return userRepository.findAll().stream()
                .map(user -> new UserMainResponseDto(user))
                .collect(Collectors.toList());
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * admin 전용
     */
    public Page<UserAdminMainResponseDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(user -> new UserAdminMainResponseDto(user));
    }

    public Page<UserAdminMainResponseDto> findByUserName(Pageable pageable, String username) {
        return userRepository.findByUserName(pageable, username)
                .map(user -> new UserAdminMainResponseDto(user));
    }

    public Page<UserAdminMainResponseDto> findByUserRole(Pageable pageable, Role role) {
        return userRepository.findByRole(pageable, role)
                .map(user -> new UserAdminMainResponseDto(user));
    }

    public UserAdminMainResponseDto toUserAdminMainResponseDto(Long id) {
        return new UserAdminMainResponseDto(getUser(id));
    }
}