package com.skhuedin.skhuedin.controller;

import com.skhuedin.skhuedin.domain.Question;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.dto.comment.CommentMainResponseDto;
import com.skhuedin.skhuedin.dto.question.QuestionMainResponseDto;
import com.skhuedin.skhuedin.dto.question.QuestionSaveRequestDto;
import com.skhuedin.skhuedin.service.CommentService;
import com.skhuedin.skhuedin.service.QuestionService;
import com.skhuedin.skhuedin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class QuestionApiController {

    private final QuestionService questionService;
    private final CommentService commentService;
    private final UserService userService;

    @GetMapping("questions/{questionId}")
    public ResponseEntity<Result> get(@PathVariable("questionId") Long id) {
        Question question = questionService.findById(id);
        List<CommentMainResponseDto> comments = commentService.findByQuestionId(id);
        QuestionMainResponseDto dto = new QuestionMainResponseDto(question, comments);

        return new ResponseEntity<>(new Result(dto), HttpStatus.OK);
    }

    @PostMapping("questions")
    public ResponseEntity<Result> create(@RequestBody QuestionSaveRequestDto dto) {
        User targetUser = userService.findById(dto.getTargetUserId());
        User writerUser = userService.findById(dto.getWriterUserId());
        dto.setTargetUser(targetUser);
        dto.setWriterUser(writerUser);

        Long saveId = questionService.save(dto);

        Question question = questionService.findById(saveId);
        QuestionMainResponseDto responseDto = new QuestionMainResponseDto(question, null);

        return new ResponseEntity<>(new Result(responseDto), HttpStatus.CREATED);
    }

    @GetMapping("users/{targetUserId}/questions")
    public ResponseEntity<Result> list(@PathVariable("targetUserId") Long id) {
        List<QuestionMainResponseDto> questions = questionService.findByTargetUserIdDesc(id);

        return new ResponseEntity<>(new Result(questions), HttpStatus.OK);
    }

    @PutMapping("questions/{questionId}")
    public ResponseEntity<Result> update(@PathVariable("questionId") Long id,
                                         @RequestBody QuestionSaveRequestDto dto) {
        Long updateId = questionService.update(id, dto);
        Question question = questionService.findById(updateId);

        List<CommentMainResponseDto> comments = commentService.findByQuestionId(question.getId());

        return new ResponseEntity<>(new Result(new QuestionMainResponseDto(question, comments)), HttpStatus.OK);
    }

    @DeleteMapping("questions/{questionsId}")
    public ResponseEntity<Result> delete(@PathVariable("questionId") Long id) {
        questionService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("questions/{questionId}/views")
    public ResponseEntity<Result> addView(@PathVariable("questionId") Long id) {
        questionService.addView(id);
        Question question = questionService.findById(id);
        List<CommentMainResponseDto> comments = commentService.findByQuestionId(id);

        return new ResponseEntity<>(new Result(new QuestionMainResponseDto(question, comments)), HttpStatus.OK);
    }
}