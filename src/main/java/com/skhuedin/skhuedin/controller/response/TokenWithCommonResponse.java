package com.skhuedin.skhuedin.controller.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenWithCommonResponse<T> extends CommonResponse {

    String token;

    public TokenWithCommonResponse(T data, String token) {
        super(data);
        this.token = token;
    }
}
