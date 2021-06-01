package com.skhuedin.skhuedin.controller.admin.api;

import com.skhuedin.skhuedin.dto.comment.CommentMainResponseDto;
import com.skhuedin.skhuedin.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentAdminApiController {
    private final CommentService commentService;

    @ResponseBody
    @PostMapping("/question/detail")
    public List<CommentMainResponseDto> commentMainList(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        return commentService.findByQuestionId(id);
    }
}
