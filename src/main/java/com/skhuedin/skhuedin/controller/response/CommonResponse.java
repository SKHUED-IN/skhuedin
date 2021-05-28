package com.skhuedin.skhuedin.controller.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommonResponse<T> extends BasicResponse {

    private T data;

    public CommonResponse(T data) {
        this.data = data;
    }
}