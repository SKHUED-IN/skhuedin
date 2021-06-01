package com.skhuedin.skhuedin.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
public class UserUpdateDto {

    @Size(max = 4, min = 4, message = "entranceYear는 4자리여야 합니다.")
    private String entranceYear;

    private String graduationYear;
}