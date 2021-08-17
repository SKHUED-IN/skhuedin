package com.skhuedin.skhuedin.controller.api;

import com.skhuedin.skhuedin.controller.response.BasicResponse;
import com.skhuedin.skhuedin.controller.response.CommonResponse;
import com.skhuedin.skhuedin.dto.comment.CommentMainResponseDto;
import com.skhuedin.skhuedin.dto.comment.CommentSaveRequestDto;
import com.skhuedin.skhuedin.security.AuthService;
import com.skhuedin.skhuedin.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;
    private final AuthService authService;

    @PostMapping("comments")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<? extends BasicResponse> save(@Valid @RequestBody CommentSaveRequestDto requestDto) {

        if (!authService.isSameUser(requestDto.getWriterUserId())) {
            throw new AccessDeniedException("동일하지 않은 유저 정보입니다.");
        }

        Long saveId = commentService.save(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResponse<>(commentService.findById(saveId)));
    }

    @GetMapping("comments/{commentId}")
    public ResponseEntity<? extends BasicResponse> findById(@PathVariable("commentId") Long id) {
        CommentMainResponseDto responseDto = commentService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(responseDto));
    }

    @PutMapping("comments/{commentId}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<? extends BasicResponse> update(@PathVariable("commentId") Long id,
                                                          @Valid @RequestBody CommentSaveRequestDto updateDto) {

        if (!authService.isSameUser(updateDto.getWriterUserId())) {
            throw new AccessDeniedException("동일하지 않은 유저 정보입니다.");
        }

        Long commentId = commentService.update(id, updateDto);
        CommentMainResponseDto responseDto = commentService.findById(commentId);

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(responseDto));
    }

    @DeleteMapping("comments/{commentId}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<? extends BasicResponse> delete(@PathVariable("commentId") Long id) {

        CommentMainResponseDto commentMainResponseDto = commentService.findById(id);

        if (!authService.isSameUser(commentMainResponseDto.getWriterUser().getId())) {
            throw new AccessDeniedException("동일하지 않은 유저 정보입니다.");
        }

        commentService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("questions/{questionId}/comments")
    public ResponseEntity<? extends BasicResponse> findByQuestionId(@PathVariable("questionId") Long questionId) {
        List<CommentMainResponseDto> comments = commentService.findByQuestionId(questionId);

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(comments));
    }
}