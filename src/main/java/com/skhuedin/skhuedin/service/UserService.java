package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.Blog;
import com.skhuedin.skhuedin.domain.Comment;
import com.skhuedin.skhuedin.domain.Posts;
import com.skhuedin.skhuedin.domain.Question;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.dto.user.UserAdminMainResponseDto;
import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;
import com.skhuedin.skhuedin.dto.user.UserSaveRequestDto;
import com.skhuedin.skhuedin.dto.user.UserUpdateDto;
import com.skhuedin.skhuedin.infra.JwtTokenProvider;
import com.skhuedin.skhuedin.infra.Role;
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
    private final JwtTokenProvider jwtTokenProvider;
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
    public void updateRole(Long id, String role) {
        User user = getUser(id);
        user.updateRole(Role.getRole(role));
    }

    @Transactional
    public void delete(Long id) {
        User findUser = userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 user 가 존재하지 않습니다. id=" + id));

        Optional<Blog> blogByUserId = blogRepository.findByUserId(id);
        List<Comment> comments = commentRepository.findByWriterUserId(id);
        List<Question> questions = questionRepository.findQuestionByUserId(id);
        List<Question> targetQuestions = questionRepository.findQuestionByTargetUserId(id);
        List<Posts> posts;

        deleteComments(comments);
        deleteQuestions(questions);
        deleteQuestions(targetQuestions);

        if (!blogByUserId.isEmpty()) {
            posts = postsRepository.findByBlogId(blogByUserId.get().getId());
            for (Posts post : posts) {
                postsRepository.deleteById(post.getId());
            }
            blogRepository.delete(blogByUserId.get());
        }
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
        User user = userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 user 가 존재하지 않습니다. id=" + id));
        return new UserMainResponseDto(user);
    }
    /**
     * 회원 가입 로직
     */
    public String createToken(String email) {
        //비밀번호 확인 등의 유효성 검사 진행
        return jwtTokenProvider.createToken(email);
    }

    public String signUp(UserSaveRequestDto requestDto) {
        save(requestDto.toEntity());
        return signIn(requestDto);
    }

    @Transactional
    public String signIn(UserSaveRequestDto requestDto) {
        User findUser = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저"));
        requestDto.addYear(findUser.getEntranceYear(), findUser.getGraduationYear());

        findUser.update(requestDto.toEntity());
        return createToken(findUser.getEmail());
    }

    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        // Bearer 검증에 null 값을 넘기기 위해 일부러 이렇게 작성함
        return user.orElse(null);
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

    public Page<UserAdminMainResponseDto> findByUserRole(Pageable pageable, String role) {
        return userRepository.findByRole(pageable, Role.getRole(role))
                .map(user -> new UserAdminMainResponseDto(user));
    }

    public UserAdminMainResponseDto toUserAdminMainResponseDto(Long id) {
        return new UserAdminMainResponseDto(getUser(id));
    }
}