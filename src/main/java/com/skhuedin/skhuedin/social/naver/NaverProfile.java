package com.skhuedin.skhuedin.social.naver;

import lombok.Data;

@Data
public class NaverProfile {

    private String resultcode;
    private String message;
    private Response response;

    @Data
    class Response {

        private String id;
        private String nickname;
        private String profile_image;
        private String age;
        private String gender;
        private String email;
        private String mobile;
        private String mobile_e164;
        private String name;
        private String birthday;
        private String birthyear;
    }
}
