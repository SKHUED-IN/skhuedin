package com.skhuedin.skhuedin.dto.user;

import com.skhuedin.skhuedin.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserMainResponseDto {

    private Long id;
    private String email;
    private String name;
    private String userImageUrl;
    private String entranceYear;
    private String graduationYear;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public UserMainResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.userImageUrl = user.getUserImageUrl();
        this.entranceYear = user.getEntranceYear();
        this.graduationYear = user.getGraduationYear();
        this.createdDate = user.getCreatedDate();
        this.lastModifiedDate = user.getLastModifiedDate();
    }
}