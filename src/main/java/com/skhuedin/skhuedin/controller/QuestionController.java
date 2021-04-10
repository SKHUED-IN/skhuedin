package com.skhuedin.skhuedin.controller;

import com.skhuedin.skhuedin.domain.Question;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.dto.question.QuestionMainResponseDto;
import com.skhuedin.skhuedin.dto.question.QuestionSaveRequestDto;
import com.skhuedin.skhuedin.service.QuestionService;
import com.skhuedin.skhuedin.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;

    @GetMapping("questions/{questionId}")
    public ResponseEntity<Result> get(@PathVariable("questionId") Long id) {

        Question question = questionService.findById(id);
        QuestionMainResponseDto dto = new QuestionMainResponseDto(question);

        return new ResponseEntity<>(new Result(dto), HttpStatus.OK);
    }

    @PostMapping("questions/{targetUserId}/writer/{writerUserId}")
    public ResponseEntity<Result> create(@PathVariable("targetUserId") Long targetUserId,
                                         @PathVariable("writerUserId") Long writerUserId,
                                         @RequestBody QuestionSaveRequestDto dto) {

        User targetUser = userService.findById(targetUserId);
        User writerUser = userService.findById(writerUserId);
        dto.setTargetUser(targetUser);
        dto.setWriterUser(writerUser);

        Long saveId = questionService.save(dto);

        Question question = questionService.findById(saveId);
        QuestionMainResponseDto responseDto = new QuestionMainResponseDto(question);

        return new ResponseEntity<>(new Result(responseDto), HttpStatus.CREATED);
    }

    @GetMapping("users/{targetUserId}/questions")
    public ResponseEntity<Result> list(@PathVariable("targetUserId") Long targetUserId) {

        List<QuestionMainResponseDto> questions = questionService.findByTargetUserIdDesc(targetUserId);
        return new ResponseEntity<>(new Result(questions), HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    class Result<T> {
        private T data;
    }
}