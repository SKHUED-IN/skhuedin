package com.skhuedin.skhuedin.dto.user;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserAddInfoRequestDto {
    LocalDateTime entranceYear;
    LocalDateTime graduationYear;

    @Builder
    public UserAddInfoRequestDto(LocalDateTime entranceYear, LocalDateTime graduationYear) {
        this.entranceYear = entranceYear;
        this.graduationYear = graduationYear;
    }
}
