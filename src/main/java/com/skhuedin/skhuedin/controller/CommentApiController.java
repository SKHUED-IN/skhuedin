package com.skhuedin.skhuedin.controller;

import com.skhuedin.skhuedin.controller.response.BasicResponse;
import com.skhuedin.skhuedin.controller.response.CommonResponse;
import com.skhuedin.skhuedin.dto.comment.CommentMainResponseDto;
import com.skhuedin.skhuedin.dto.comment.CommentSaveRequestDto;
import com.skhuedin.skhuedin.infra.MyRole;
import com.skhuedin.skhuedin.service.CommentService;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

    @MyRole
    @PostMapping("comments")
    public ResponseEntity<? extends BasicResponse> save(@RequestBody CommentSaveRequestDto requestDto) {
        Long saveId = commentService.save(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResponse<>(commentService.findById(saveId)));
    }

    @GetMapping("comments/{commentId}")
    public ResponseEntity<? extends BasicResponse> findById(@PathVariable("commentId") Long id) {
        CommentMainResponseDto responseDto = commentService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(responseDto));
    }

    @MyRole
    @PutMapping("comments/{commentId}")
    public ResponseEntity<? extends BasicResponse> update(@PathVariable("commentId") Long id,
                                                          @RequestBody CommentSaveRequestDto updateDto) {
        Long commentId = commentService.update(id, updateDto);
        CommentMainResponseDto responseDto = commentService.findById(commentId);

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(responseDto));
    }

    @MyRole
    @DeleteMapping("comments/{commentId}")
    public ResponseEntity<? extends BasicResponse> delete(@PathVariable("commentId") Long id) {
        commentService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new CommonResponse<>("삭제에 성공하였습니다."));
    }

    @GetMapping("questions/{questionId}/comments")
    public ResponseEntity<? extends BasicResponse> findByQuestionId(@PathVariable("questionId") Long questionId) {
        List<CommentMainResponseDto> comments = commentService.findByQuestionId(questionId);

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(comments));
    }
}