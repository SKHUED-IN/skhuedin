package com.skhuedin.skhuedin.kakao;

import lombok.Data;

@Data
public class OAuthToken {

    /*
    기존의 문법과 어긋나는 변수명이지만, 카카오에서 리스폰즈로 오는 값의 이름과 통일 시키기 위함임.
     */
    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private int refresh_token_expires_in;
}
