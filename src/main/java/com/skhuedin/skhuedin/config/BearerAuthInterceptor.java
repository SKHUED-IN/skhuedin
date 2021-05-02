package com.skhuedin.skhuedin.config;

import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.infra.JwtTokenProvider;
import com.skhuedin.skhuedin.infra.MyRole;
import com.skhuedin.skhuedin.infra.Role;
import com.skhuedin.skhuedin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
@RequiredArgsConstructor
public class BearerAuthInterceptor implements HandlerInterceptor {
    private AuthorizationExtractor authExtractor;
    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        log.info(">>> interceptor.preHandle 호출");

        // handler 종류 확인 => HandlerMethod 타입인지 체크
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        // 형 변환 하기
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        // @MyRole 받아오기
        MyRole mySecured = handlerMethod.getMethodAnnotation(MyRole.class);


        /**
         * 지금은 Controller 에 @MyRole을 달지 않았기 때문에 주석 표시.
         */
        // method에 @MyRole가 없는 경우, 즉 인증이 필요 없는 요청
//        if (mySecured == null) {
//            return true;
//        }

        // @MySecured가 있는 경우, 인증이 필요하기 때문에 request header에서 token 값 추출
        //값이 있으면 토큰 값을 저장한다.
        String token = authExtractor.extract(request, "Bearer");

        // 값이 비어있으면 ture 를 반환한다.
        if (token.isEmpty()) {
            return true;
        }
        // 토큰이 유효한지 테스트 한다.
        if (!jwtTokenProvider.validateToken(token)) {
            throw new IllegalArgumentException("유효하지 않은 토큰");
        }

        /**
         * USER, ADMIN 권한 검사 로직
         */
        // token을 활용하여 user email 추출
        String email = jwtTokenProvider.getSubject(token);
        User user = userService.findByEmail(email);

        // @MySecured의 Role이 admin 권한인 경우
        String role = mySecured.role().toString();
        if (role != null && user != null) {
            if ("ADMIN".equals(role)) {
                if (user.getRole() != Role.ADMIN) {
                    response.sendRedirect("/my-error");
                    return false;
                }
            }
        }
        //값이 있으면 그 값을 request.
        request.setAttribute("name", email);
        // 접근 허가
        return true;
    }
}
