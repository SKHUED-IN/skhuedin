package com.skhuedin.skhuedin.controller.admin.api;

import com.skhuedin.skhuedin.dto.category.CategoryMainResponseDto;
import com.skhuedin.skhuedin.dto.category.CategoryRequestDto;
import com.skhuedin.skhuedin.infra.MyRole;
import com.skhuedin.skhuedin.infra.Role;
import com.skhuedin.skhuedin.service.CategoryService;
import com.skhuedin.skhuedin.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CategoryAdminApiController {

    private final PostsService postsService;
    private final CategoryService categoryService;

    @GetMapping("/category")
    @ResponseStatus(HttpStatus.OK)
    public String categoryMainList() {
        return "contents/categoryList";
    }

    @GetMapping("/create/category")
    @ResponseStatus(HttpStatus.OK)
    public String createCatetory() {
        return "contents/createCategory";
    }

    @PostMapping("update/category")
    @ResponseStatus(HttpStatus.OK)
    public String updateCategory(@RequestParam(value = "category", required = false, defaultValue = "0") Long category,
                                 @RequestParam(value = "post_id", required = false, defaultValue = "0") Long post_id) {
        postsService.update(post_id, category);
        return "redirect:/postList";
    }

    @PostMapping("category/up")
    @ResponseStatus(HttpStatus.OK)
    public String upCategory(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        categoryService.addWeight(id);
        return "redirect:/categoryList";
    }

    @PostMapping("category/down")
    @ResponseStatus(HttpStatus.OK)
    public String downCategory(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        categoryService.subtractWeight(id);
        return "redirect:/categoryList";
    }

    @PostMapping("create/category")
    @ResponseStatus(HttpStatus.OK)
    public String createCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        categoryService.save(categoryRequestDto);
        return "redirect:/categoryList";
    }

    @PostMapping("category/delete")
    @ResponseStatus(HttpStatus.OK)
    public String deleteCategory(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        categoryService.delete(id);
        return "redirect:/categoryList";
    }

    @MyRole(role = Role.ADMIN)
    @ResponseBody
    @PostMapping("/category")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryMainResponseDto> categoryList() {
        List<CategoryMainResponseDto> list = categoryService.findAll();
        for (CategoryMainResponseDto category : list) {
            category.add(postsService.findByCategoryId(category.getId()));
        }
        return list;
    }
}
