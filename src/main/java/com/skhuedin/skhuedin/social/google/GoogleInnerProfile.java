package com.skhuedin.skhuedin.social.google;

import lombok.Builder;
import lombok.Data;

@Data
public class GoogleInnerProfile {

    @Builder
    public GoogleInnerProfile(String typ, String iss, String azp, String aud, String sub, String iat, String exp,
                              String hd, String alg, String kid, String jti, String email, String email_verified,
                              String at_hash, String name, String picture, String given_name, String family_name,
                              String locale, String nonce, String profile) {
        this.typ = typ;
        this.iss = iss;
        this.azp = azp;
        this.aud = aud;
        this.sub = sub;
        this.iat = iat;
        this.exp = exp;
        this.hd = hd;
        this.alg = alg;
        this.kid = kid;
        this.jti = jti;
        this.email = email;
        this.email_verified = email_verified;
        this.at_hash = at_hash;
        this.name = name;
        this.picture = picture;
        this.given_name = given_name;
        this.family_name = family_name;
        this.locale = locale;
        this.nonce = nonce;
        this.profile = profile;
    }

    public String typ;
    public String iss;
    public String azp;
    public String aud;
    public String sub;
    public String iat;
    public String exp;
    public String hd;
    public String alg;
    public String kid;
    public String jti;

    public String email;
    public String email_verified;
    public String at_hash;
    public String name;
    public String picture;
    public String given_name;
    public String family_name;
    public String locale;
    public String nonce;
    public String profile;

}