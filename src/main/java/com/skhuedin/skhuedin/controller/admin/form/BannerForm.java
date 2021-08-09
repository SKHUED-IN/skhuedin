package com.skhuedin.skhuedin.controller.admin.form;

import com.skhuedin.skhuedin.domain.banner.Banner;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class BannerForm {

    private String title;
    private String content;
    private Integer weight;
    private MultipartFile imageFile;

    public BannerForm(Banner banner) {
        this.title = banner.getTitle();
        this.content = banner.getContent();
        this.weight = banner.getWeight();
    }
}