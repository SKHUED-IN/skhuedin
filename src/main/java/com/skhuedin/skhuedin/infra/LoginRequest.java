package com.skhuedin.skhuedin.infra;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {

    private String email;
    private String pwd;

    public LoginRequest(String email, String pwd) {
        this.email = email;
        this.pwd = pwd;
    }
}
