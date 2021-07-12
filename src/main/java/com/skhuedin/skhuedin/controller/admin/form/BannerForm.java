package com.skhuedin.skhuedin.controller.admin.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class BannerForm {

    private String title;
    private String content;
    private MultipartFile imageFile;
}