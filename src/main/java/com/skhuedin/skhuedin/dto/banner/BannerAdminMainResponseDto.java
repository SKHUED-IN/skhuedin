package com.skhuedin.skhuedin.dto.banner;

import com.skhuedin.skhuedin.domain.banner.Banner;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class BannerAdminMainResponseDto {

    private final Long id;
    private final String title;
    private final String content;
    private final Integer weight;
    private final String createdDate;
    private final String lastModifiedDate;

    public BannerAdminMainResponseDto(Banner banner) {
        this.id = banner.getId();
        this.title = banner.getTitle();
        this.content = banner.getContent();
        this.weight = banner.getWeight();
        this.createdDate = banner.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.lastModifiedDate = banner.getLastModifiedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}