package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.Comment;
import com.skhuedin.skhuedin.domain.Question;
import com.skhuedin.skhuedin.domain.user.User;
import com.skhuedin.skhuedin.dto.comment.CommentMainResponseDto;
import com.skhuedin.skhuedin.dto.comment.CommentSaveRequestDto;
import com.skhuedin.skhuedin.repository.CommentRepository;
import com.skhuedin.skhuedin.repository.QuestionRepository;
import com.skhuedin.skhuedin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Long save(CommentSaveRequestDto requestDto) {
        Question question = getQuestion(requestDto.getQuestionId());
        User writerUser = getUser(requestDto.getWriterUserId());

        Comment comment = requestDto.toEntity(question, writerUser);
        return commentRepository.save(comment).getId();
    }

    @Transactional
    public Long update(Long id, CommentSaveRequestDto requestDto) {
        Question question = getQuestion(requestDto.getQuestionId());
        User writerUser = getUser(requestDto.getWriterUserId());
        Comment comment = getComment(id);

        comment.updateComment(requestDto.toEntity(question, writerUser));

        return comment.getId();
    }

    @Transactional
    public void delete(Long id) {
        Comment comment = getComment(id);
        commentRepository.delete(comment);
    }

    public CommentMainResponseDto findById(Long id) {
        Comment comment = getComment(id);
        return new CommentMainResponseDto(comment);
    }

    public List<CommentMainResponseDto> findByQuestionId(Long questionId) {
        return commentRepository.findByQuestionId(questionId).stream()
                .map(comment -> new CommentMainResponseDto(comment))
                .collect(Collectors.toList());
    }

    /* private 메소드 */
    private Question getQuestion(Long id) {
        return questionRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 question 이 존재하지 않습니다. id=" + id));
    }

    private User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 user 가 존재하지 않습니다. id=" + id));
    }

    private Comment getComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 comment 가 존재하지 않습니다. id=" + id));
    }
}