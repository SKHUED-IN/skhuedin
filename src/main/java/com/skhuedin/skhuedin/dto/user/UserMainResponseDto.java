package com.skhuedin.skhuedin.dto.user;


import com.skhuedin.skhuedin.domain.User;
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
    LocalDateTime entranceYear;
    LocalDateTime graduationYear;
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;

    public UserMainResponseDto(User user) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.userImageUrl = userImageUrl;
        this.entranceYear = entranceYear;
        this.graduationYear = graduationYear;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}