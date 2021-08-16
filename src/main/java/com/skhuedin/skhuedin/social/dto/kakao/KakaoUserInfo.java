package com.skhuedin.skhuedin.social.dto.kakao;

import com.skhuedin.skhuedin.domain.user.Provider;
import com.skhuedin.skhuedin.domain.user.Role;
import com.skhuedin.skhuedin.domain.user.User;
import com.skhuedin.skhuedin.social.dto.UserInfo;
import lombok.Data;

@Data
public class KakaoUserInfo implements UserInfo {

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

    @Override
    public User toEntity() {
        return User.builder()
                .email(this.kakao_account.email)
                .name(this.properties.nickname)
                .provider(Provider.KAKAO)
                .userImageUrl(this.kakao_account.profile.profile_image_url)
                .role(Role.ROLE_USER)
                .build();
    }

    @Override
    public String getEmail() {
        return this.kakao_account.email;
    }
}