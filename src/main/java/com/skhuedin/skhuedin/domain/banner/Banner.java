package com.skhuedin.skhuedin.domain.banner;

import com.skhuedin.skhuedin.domain.BaseEntity;
import com.skhuedin.skhuedin.domain.UploadFile;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Banner extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "banner_id")
    private Long id;

    private String title;

    private String content;

    private Integer weight;

    @Embedded
    private UploadFile uploadFile;

    @Builder
    public Banner(String title, String content, Integer weight, UploadFile uploadFile) {
        this.title = title;
        this.content = content;
        this.weight = weight;
        this.uploadFile = uploadFile;
    }

    public void updateBanner(Banner banner) {
        this.title = banner.title;
        this.content = banner.content;
        this.weight = banner.weight;
        if (banner.getUploadFile() != null) {
            this.uploadFile = banner.getUploadFile();
        }
    }
}