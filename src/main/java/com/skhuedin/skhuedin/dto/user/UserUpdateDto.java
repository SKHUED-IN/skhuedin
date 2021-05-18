package com.skhuedin.skhuedin.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
public class UserUpdateDto {

    @NotNull(message = "user의 id는 null이 될 수 없습니다.")
    private Long id;

    @Size(max = 4, min = 4, message = "entranceYear는 4자리여야 합니다.")
    private String entranceYear;

    private String graduationYear;
}