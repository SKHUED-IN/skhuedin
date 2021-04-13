package com.skhuedin.skhuedin.controller;

import com.skhuedin.skhuedin.domain.Comment;
import com.skhuedin.skhuedin.domain.Question;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.dto.comment.CommentMainResponseDto;
import com.skhuedin.skhuedin.dto.comment.CommentSaveRequestDto;
import com.skhuedin.skhuedin.service.CommentService;
import com.skhuedin.skhuedin.service.QuestionService;
import com.skhuedin.skhuedin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;
    private final QuestionService questionService;
    private final UserService userService;

    @PostMapping("comments")
    public ResponseEntity<Result> create(@RequestBody CommentSaveRequestDto dto) {
        Question question = questionService.findById(dto.getQuestionId());
        User user = userService.findById(dto.getWriterUserId());
        dto.setQuestion(question);
        dto.setWriterUser(user);

        Long commentId = commentService.save(dto);
        Comment comment = commentService.findById(commentId);

        return new ResponseEntity<>(new Result<>(new CommentMainResponseDto(comment)), HttpStatus.CREATED);
    }

    @PutMapping("comments/{commentId}")
    public ResponseEntity<Result> update(@PathVariable("commentId") Long id,
                                         @RequestBody CommentSaveRequestDto dto) {
        Question question = questionService.findById(dto.getWriterUserId());
        User user = userService.findById(dto.getWriterUserId());
        dto.setQuestion(question);
        dto.setWriterUser(user);

        Long commentId = commentService.update(id, dto);
        Comment comment = commentService.findById(commentId);

        return new ResponseEntity<>(new Result(new CommentMainResponseDto(comment)), HttpStatus.OK);
    }

    @DeleteMapping("comments/{commentId}")
    public ResponseEntity<Result> delete(@PathVariable("commentId") Long id) {
        commentService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}