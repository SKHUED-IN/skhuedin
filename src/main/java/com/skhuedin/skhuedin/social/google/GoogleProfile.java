package com.skhuedin.skhuedin.social.google;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GoogleProfile {
    String id;
    String email;
    boolean verified_email;
    String name;
    String given_name;
    String family_name;
    String picture;
    String locale;

    @Builder
    public GoogleProfile(String id, String email, boolean verified_email, String name, String given_name, String family_name, String picture, String locale) {
        this.id = id;
        this.email = email;
        this.verified_email = verified_email;
        this.name = name;
        this.given_name = given_name;
        this.family_name = family_name;
        this.picture = picture;
        this.locale = locale;
    }
}