package com.skhuedin.skhuedin.infra;

import java.util.Locale;

public enum Role {
    ADMIN,
    USER;

    public Role getRole(String role) {
        if (role.toUpperCase(Locale.ROOT).equals("ADMIN")) {
            return ADMIN;
        } else {
            return USER;
        }
    }
}
