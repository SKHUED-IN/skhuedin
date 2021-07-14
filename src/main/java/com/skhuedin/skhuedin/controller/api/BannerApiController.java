package com.skhuedin.skhuedin.controller.api;

import com.skhuedin.skhuedin.controller.response.BasicResponse;
import com.skhuedin.skhuedin.controller.response.CommonResponse;
import com.skhuedin.skhuedin.file.FileStore;
import com.skhuedin.skhuedin.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BannerApiController {

    private final BannerService bannerService;
    private final FileStore fileStore;

    @GetMapping("banners")
    public ResponseEntity<? extends BasicResponse> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(bannerService.findAll()));
    }

    @GetMapping("banners/images/{storeFileName}")
    public Resource downloadImage(@PathVariable String storeFileName) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(storeFileName));
    }
}