package com.skhuedin.skhuedin.social.google;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class GoogleInnerProfile {

    public GoogleInnerProfile(String iss, String azp, String aud, String sub, String atHash,
                              String name, String picture, String givenName, String familyName,
                              String locale, String iat, String exp, String alg, String kid, String typ, String email,
                              Map<String, Object> additionalProperties) {
        this.iss = iss;
        this.azp = azp;
        this.aud = aud;
        this.sub = sub;
        this.atHash = atHash;
        this.name = name;
        this.picture = picture;
        this.givenName = givenName;
        this.familyName = familyName;
        this.locale = locale;
        this.iat = iat;
        this.exp = exp;
        this.alg = alg;
        this.kid = kid;
        this.typ = typ;
        this.additionalProperties = additionalProperties;
        this.email = email;
    }

    public GoogleInnerProfile() {

    }

    public String iss;
    public String azp;
    public String aud;
    public String sub;
    public String atHash;
    public String name;
    public String picture;
    public String givenName;
    public String familyName;
    public String locale;
    public String iat;
    public String exp;
    public String alg;
    public String kid;
    public String typ;
    public String email;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}