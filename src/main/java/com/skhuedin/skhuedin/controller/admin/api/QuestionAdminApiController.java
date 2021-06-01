package com.skhuedin.skhuedin.controller.admin.api;

import com.skhuedin.skhuedin.dto.question.QuestionMainResponseDto;
import com.skhuedin.skhuedin.infra.MyRole;
import com.skhuedin.skhuedin.infra.Role;
import com.skhuedin.skhuedin.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class QuestionAdminApiController {
    private final QuestionService questionService;

    @GetMapping("/questionList")
    public String questionMainList() {
        return "contents/questionList";
    }

    @PostMapping("question/status")
    public String questionStatus(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        questionService.updateStatus(id);
        return "redirect:/questionList";
    }

    @MyRole(role = Role.ADMIN)
    @ResponseBody
    @PostMapping("/questionList")
    public List<QuestionMainResponseDto> questionList(Pageable pageable) {
        List<QuestionMainResponseDto> list = questionService.findAll(pageable);
        return list;
    }

}
