package com.skhuedin.skhuedin.social.kakao;

import lombok.Data;

@Data
public class KakaoProfile {

    private Integer id;
    private String connected_at;
    private Properties properties;
    private Kakao_account kakao_account;

    @Data
    public class Properties {

        private String nickname;
        private String profile_image;
        private String thumbnail_image;
    }

    @Data
    public class Kakao_account {

        private Boolean profile_needs_agreement;
        private Profile profile;
        private Boolean has_email;
        private Boolean email_needs_agreement;
        private Boolean is_email_valid;
        private Boolean is_email_verified;
        private String email;

        @Data
        public class Profile {

            private String nickname;
            private String thumbnail_image_url;
            private String profile_image_url;
            private Boolean is_default_image;
        }
    }
}





