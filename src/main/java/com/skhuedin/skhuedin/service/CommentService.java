package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.Comment;
import com.skhuedin.skhuedin.dto.comment.CommentMainResponseDto;
import com.skhuedin.skhuedin.dto.comment.CommentSaveRequestDto;
import com.skhuedin.skhuedin.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public Long save(CommentSaveRequestDto dto) {
        Comment comment = dto.toEntity();
        if (dto.getParentCommentId() != null) { // 부모 comment 가 있다면
            Comment parent = commentRepository.findById(comment.getId())
                    .orElseThrow(() -> new IllegalArgumentException("parent comment 가 존재하지 않습니다."));

            parent.addChild(comment);
        }

        return commentRepository.save(comment).getId();
    }

    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new IllegalArgumentException("comment 가 존재하지 않습니다."));
    }

    public List<CommentMainResponseDto> findByQuestionId(Long questionId) {
        return commentRepository.findByQuestionId(questionId)
                .stream()
                .map(comment -> new CommentMainResponseDto(comment))
                .collect(Collectors.toList());
    }

    @Transactional
    public Long update(Long id, CommentSaveRequestDto dto) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("comment 가 존재하지 않습니다."));

        comment.updateComment(dto.toEntity());

        return comment.getId();
    }

    @Transactional
    public void delete(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("comment 가 존재하지 않습니다."));

        commentRepository.delete(comment);
    }
}