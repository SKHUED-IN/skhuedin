package com.skhuedin.skhuedin.dto.user;

import com.skhuedin.skhuedin.domain.user.Provider;
import com.skhuedin.skhuedin.domain.user.Role;
import com.skhuedin.skhuedin.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserMainResponseDto {

    private Long id;
    private String email;
    private String name;
    private String userImageUrl;
    private Provider provider;
    private Role role;
    private String entranceYear;
    private String graduationYear;
    private Boolean isBlog;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public UserMainResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.provider = user.getProvider();
        this.role = user.getRole();
        this.userImageUrl = user.getUserImageUrl();
        this.entranceYear = user.getEntranceYear();
        this.graduationYear = user.getGraduationYear();
        this.isBlog = user.getBlog() != null;
        this.createdDate = user.getCreatedDate();
        this.lastModifiedDate = user.getLastModifiedDate();
    }
}