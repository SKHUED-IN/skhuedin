package com.skhuedin.skhuedin.interceptor;

import com.skhuedin.skhuedin.common.exception.EmptyTokenException;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.infra.JwtTokenProvider;
import com.skhuedin.skhuedin.infra.MyRole;
import com.skhuedin.skhuedin.infra.Role;
import com.skhuedin.skhuedin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
@RequiredArgsConstructor
public class BearerAuthInterceptor implements HandlerInterceptor {

    private final AuthorizationExtractor authExtractor;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // handler 종류 확인 => HandlerMethod 타입인지 체크
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 형 변환 하기
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        // @MyRole 받아오기
        MyRole myRole = handlerMethod.getMethodAnnotation(MyRole.class);

        // method에 @MyRole가 없는 경우, 즉 인증이 필요 없는 요청
        if (myRole == null) {
            return true;
        }

        //값이 있으면 토큰 값을 저장한다.
        String token = authExtractor.extract(request, "Bearer");

        // 값이 비어있으면 ture 를 반환한다.
        if (token.isEmpty()) {
            throw new EmptyTokenException("token이 비어 있습니다.");
        }

        // 토큰이 유효한지 테스트 한다.
        if (!jwtTokenProvider.validateToken(token)) {
            throw new EmptyTokenException("token이 유효하지 않습니다.");
        }

        // token을 활용하여 user email 추출
        String email = jwtTokenProvider.getSubject(token);
        User user = userService.findByEmail(email);

        if (user == null) {
            throw new IllegalArgumentException("존재하지 않는 user 입니다.");
        }

        // @MyRole의 Role이 admin 권한인 경우
        String role = myRole.role().toString();
        if ("ADMIN".equals(role)) {
            if (user.getRole() != Role.ADMIN) {
                throw new IllegalArgumentException("접근할 수 없는 권한입니다.");
            }
        }

        // 접근 허가
        return true;
    }
}
