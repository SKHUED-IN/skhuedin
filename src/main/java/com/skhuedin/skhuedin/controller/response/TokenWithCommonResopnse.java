package com.skhuedin.skhuedin.controller.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TokenWithCommonResopnse extends CommonResponse {

    String token;

    public TokenWithCommonResopnse(Object data, String token) {
        super(data);
        this.token = token;
    }
}
