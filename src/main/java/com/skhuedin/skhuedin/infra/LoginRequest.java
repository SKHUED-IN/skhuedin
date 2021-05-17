package com.skhuedin.skhuedin.infra;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {

    private String name;
    private String pwd;

    public LoginRequest(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }
}
