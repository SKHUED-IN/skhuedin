package com.skhuedin.skhuedin.dto.user;

import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.infra.Role;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class UserAdminMainResponseDto {

    private final Long id;
    private final String email;
    private final String name;
    private final String userImageUrl;
    private final Provider provider;
    private final Role role;
    private final String entranceYear;
    private final String graduationYear;
    private final Boolean isBlog;
    private final String createdDate;
    private final String lastModifiedDate;

    public UserAdminMainResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.provider = user.getProvider();
        this.role = user.getRole();
        this.userImageUrl = user.getUserImageUrl();
        this.entranceYear = user.getEntranceYear();
        this.graduationYear = user.getGraduationYear();
        this.isBlog = user.getBlog() != null;
        this.createdDate = user.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.lastModifiedDate = user.getLastModifiedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}