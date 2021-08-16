package com.skhuedin.skhuedin.controller.admin.api;

import com.skhuedin.skhuedin.controller.response.BasicResponse;
import com.skhuedin.skhuedin.controller.response.CommonResponse;
import com.skhuedin.skhuedin.service.PostsService;
import com.skhuedin.skhuedin.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminQuestionsApiController {

    private final PostsService postsService;
    private final QuestionService questionService;

    @GetMapping("questions")
    public ResponseEntity<? extends BasicResponse> getQuestions(
            Pageable pageable,
            @RequestParam(name = "writerUser", defaultValue = "") String writerUser,
            @RequestParam(name = "targetUser", defaultValue = "") String targetUser) {

        if (!writerUser.isEmpty() && targetUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new CommonResponse<>(questionService.findByWriterUserName(pageable, writerUser)));
        } else if (writerUser.isEmpty() && !targetUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new CommonResponse<>(questionService.findByTargetUserName(pageable, targetUser)));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new CommonResponse<>(questionService.findAll(pageable)));
        }
    }

    @GetMapping("questions/{questionId}")
    public ResponseEntity<? extends BasicResponse> getQuestions(@PathVariable("questionId") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(questionService.findById(id)));
    }

    @GetMapping("suggestions")
    public ResponseEntity<? extends BasicResponse> suggestions(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(postsService.findSuggestions(pageable)));
    }
}
