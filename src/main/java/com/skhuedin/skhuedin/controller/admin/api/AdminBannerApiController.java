package com.skhuedin.skhuedin.controller.admin.api;

import com.skhuedin.skhuedin.controller.admin.form.BannerForm;
import com.skhuedin.skhuedin.controller.response.BasicResponse;
import com.skhuedin.skhuedin.controller.response.CommonResponse;
import com.skhuedin.skhuedin.infra.MyRole;
import com.skhuedin.skhuedin.infra.Role;
import com.skhuedin.skhuedin.service.BannerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminBannerApiController {

    private final BannerService bannerService;

    @MyRole(role = Role.ADMIN)
    @PostMapping("banners")
    public ResponseEntity<? extends BasicResponse> save(@ModelAttribute BannerForm bannerForm) throws IOException {

        Long saveId = bannerService.save(bannerForm);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResponse<>(bannerService.findById(saveId)));
    }

    @MyRole(role = Role.ADMIN)
    @GetMapping("banners")
    public ResponseEntity<? extends BasicResponse> findAll(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(bannerService.findAll(pageable)));
    }

    @MyRole(role = Role.ADMIN)
    @GetMapping("banners/{bannerId}")
    public ResponseEntity<? extends BasicResponse> findById(@PathVariable Long bannerId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(bannerService.findById(bannerId)));
    }

    @MyRole(role = Role.ADMIN)
    @PutMapping("banners/{bannerId}")
    public ResponseEntity<? extends BasicResponse> update(@PathVariable Long bannerId,
                                                          @ModelAttribute BannerForm bannerForm) throws IOException {
        bannerService.update(bannerId, bannerForm);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(bannerService.findById(bannerId)));
    }

    @MyRole(role = Role.ADMIN)
    @DeleteMapping("banners/{bannerId}")
    public ResponseEntity<? extends BasicResponse> delete(@PathVariable Long bannerId) {
        bannerService.delete(bannerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}