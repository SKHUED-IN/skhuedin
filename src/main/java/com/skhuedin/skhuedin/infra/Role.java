package com.skhuedin.skhuedin.infra;

import java.util.Locale;

public enum Role {
    ADMIN,
    USER;

    public static Role getRole(String role) {

        if (role.toUpperCase(Locale.ROOT).equals("USER")) {
            return Role.USER;
        } else if (role.toUpperCase(Locale.ROOT).equals("ADMIN")) {
            return Role.ADMIN;
        } else throw new IllegalArgumentException("존재하지 않는 Role 입니다.");
    }
}
