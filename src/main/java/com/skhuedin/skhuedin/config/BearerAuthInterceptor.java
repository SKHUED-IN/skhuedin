package com.skhuedin.skhuedin.config;

import com.skhuedin.skhuedin.infra.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class BearerAuthInterceptor implements HandlerInterceptor {
    private AuthorizationExtractor authExtractor;
    private JwtTokenProvider jwtTokenProvider;

    public BearerAuthInterceptor(AuthorizationExtractor authExtractor, JwtTokenProvider jwtTokenProvider) {
        this.authExtractor = authExtractor;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) {
        log.info(">>> interceptor.preHandle 호출");
        String token = authExtractor.extract(request, "Bearer");
        //값이 있으면 토큰 값을 저장한다.

        if (Strings.isEmpty(token)) { // 값이 비어있으면 ture 를 반환한다.
            return true;
        }

        if (!jwtTokenProvider.validateToken(token)) {
            throw new IllegalArgumentException("유효하지 않은 토큰");
        }
        //값이 있으면 그 값을 request.
        String name = jwtTokenProvider.getSubject(token);
        request.setAttribute("name", name);
        return true;
    }
}
