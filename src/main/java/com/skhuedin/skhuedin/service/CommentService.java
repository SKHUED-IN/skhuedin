package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.Comment;
import com.skhuedin.skhuedin.domain.Question;
import com.skhuedin.skhuedin.domain.User;
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
        Comment parent;
        if (requestDto.getParentId() != null) { // 부모 comment 가 존재하면 대댓글
            parent = getComment(requestDto.getParentId());
        } else { // 부모 comment 가 null 이면 최상위 댓글
            parent = null;
        }

        Comment comment = requestDto.toEntity(question, writerUser, parent);
        return commentRepository.save(comment).getId();
    }

    @Transactional
    public Long update(Long id, CommentSaveRequestDto requestDto) {
        Question question = getQuestion(requestDto.getQuestionId());
        User writerUser = getUser(requestDto.getWriterUserId());
        Comment comment = getComment(id);
        if (requestDto.getParentId() != null) { // 부모 comment 가 존재하면 대댓글
            Comment parent = getComment(requestDto.getParentId());
            comment.updateComment(requestDto.toEntity(question, writerUser, parent));
        } else { // 부모 comment 가 null 이면 부모 댓글
            comment.updateComment(requestDto.toEntity(question, writerUser));
        }

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
        List<Comment> parents = commentRepository.findByQuestionId(questionId); // 부모 comment 목록 조회

        return parents.stream()
                .map(comment -> {
                    List<Comment> children = commentRepository.findByParentId(comment.getId());
                    return new CommentMainResponseDto(comment, children);
                }).collect(Collectors.toList());
    }

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