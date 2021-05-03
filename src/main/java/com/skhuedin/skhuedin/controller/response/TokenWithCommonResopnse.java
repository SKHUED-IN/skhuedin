package com.skhuedin.skhuedin.controller.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TokenWithCommonResopnse<T> extends CommonResponse {

    String token;

    public TokenWithCommonResopnse(T data, String token) {
        super(data);
        this.token = token;
    }
}
