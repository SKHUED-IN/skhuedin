package com.skhuedin.skhuedin.controller.admin.api;

import com.skhuedin.skhuedin.controller.response.BasicResponse;
import com.skhuedin.skhuedin.controller.response.CommonResponse;
import com.skhuedin.skhuedin.dto.category.CategoryRequestDto;
import com.skhuedin.skhuedin.dto.user.UserAdminUpdateRequestDto;
import com.skhuedin.skhuedin.service.CategoryService;
import com.skhuedin.skhuedin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminCategoryApiController {
    private final CategoryService categoryService;

    //@MyRole(role = Role.ADMIN)
    @GetMapping("/category")
    public ResponseEntity<? extends BasicResponse> getCategory(
            Pageable pageable,
            @RequestParam(name = "cmd", defaultValue = "") String cmd
    ) {
        if (cmd.equals("")) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new CommonResponse<>(categoryService.findAll(pageable)));
        } else if (cmd.equals("1")) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new CommonResponse<>(categoryService.findByWeightPage(pageable)));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new CommonResponse<>(categoryService.findByCreatedDate(pageable)));
        }
    }

    //    //    @MyRole(role = Role.ADMIN)
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<? extends BasicResponse> getCategory(@PathVariable("categoryId") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(categoryService.findByIdByAdmin(id)));
    }

    @GetMapping("/category/up/{categoryId}")
    public ResponseEntity<? extends BasicResponse> upCategory(@PathVariable("categoryId") Long id) {
        categoryService.addWeight(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/category/down/{categoryId}")
    public ResponseEntity<? extends BasicResponse> downCategory(@PathVariable("categoryId") Long id) {
        categoryService.subtractWeight(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/category")
    public ResponseEntity<? extends BasicResponse> createCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        categoryService.save(categoryRequestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<? extends BasicResponse> deleteUsers(@PathVariable("categoryId") Long id) {
        categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
