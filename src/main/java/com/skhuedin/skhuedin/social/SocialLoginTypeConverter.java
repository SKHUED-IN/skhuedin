package com.skhuedin.skhuedin.social;

import com.skhuedin.skhuedin.domain.Provider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
@Slf4j
public class SocialLoginTypeConverter implements Converter<String, Provider> {

    /**
     * SocialLoginTypeConverter는 Controller에서 socialLoginType
     * 파라미터(@PathVariable을 통해)를 받는데 enum 타입의 대문자 값을 소문자로 mapping
     * 가능하도록 하기위하여 생성
     */
    @Override
    public Provider convert(String s) {
        return Provider.valueOf(s.toUpperCase());

    }
}