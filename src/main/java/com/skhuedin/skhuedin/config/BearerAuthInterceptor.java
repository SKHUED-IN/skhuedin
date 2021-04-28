package com.skhuedin.skhuedin.config;

import com.skhuedin.skhuedin.infra.JwtTokenProvider;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
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
        System.out.println(">>> interceptor.preHandle 호출");
        String token = authExtractor.extract(request, "Bearer");
        //값이 있으면 토큰 값을 저장한다.

        if (StringUtils.isEmpty(token)) { // 값이 비어있으면 ture 를 반환한다.
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
