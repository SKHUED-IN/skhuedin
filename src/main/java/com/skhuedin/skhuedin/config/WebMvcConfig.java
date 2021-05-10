package com.skhuedin.skhuedin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final HandlerInterceptor bearerAuthInterceptor;

    public WebMvcConfig(HandlerInterceptor bearerAuthInterceptor) {
        this.bearerAuthInterceptor = bearerAuthInterceptor;
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(bearerAuthInterceptor)
                .addPathPatterns("/**")     // 모든 페이지에 적용
                .excludePathPatterns("/static/**"); // 정적 파일 제외
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/api/**")
                .allowedOrigins("http://localhost:3000");
    }
}
