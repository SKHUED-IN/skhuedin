package com.skhuedin.skhuedin.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserUpdateDto {

    private Long id;
    private LocalDate entranceYear;
    private LocalDate graduationYear;
}