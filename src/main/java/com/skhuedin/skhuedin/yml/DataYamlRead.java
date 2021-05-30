package com.skhuedin.skhuedin.yml;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@Configuration
//value를 통해 값이 있는 위치를 명시해준다.
@PropertySource(value = "classpath:data.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "my")
public class DataYamlRead {

    private String age;
    private String kakao;
    private String googleReqURL;
    private String kakaoUrl;
    private String naver;


}
