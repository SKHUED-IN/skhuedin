package com.skhuedin.skhuedin.config;

import com.skhuedin.skhuedin.jwt.JwtAccessDeniedHandler;
import com.skhuedin.skhuedin.jwt.JwtAuthenticationEntryPoint;
import com.skhuedin.skhuedin.jwt.JwtSecurityConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtSecurityConfig jwtSecurityConfig;
    private final AuthenticationProvider authenticationProvider;

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth.
                authenticationProvider(authenticationProvider);
    }

    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers(
                        "/h2-console/**",
                        "/favicon.ico",
                        "/images/**",
                        "/css/**",
                        "/js/**",
                        "/profile/**",
                        "/error",
                        "/v2/api-docs", "/swagger-resources/**",
                        "/swagger-ui.html", "/webjars/**", "/swagger/**"
                );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/auth/google/callback").permitAll()
                .antMatchers("/auth/kakao/callback").permitAll()
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .antMatchers("/api/**").permitAll()
                .antMatchers("/admin/**").permitAll()

                .anyRequest().authenticated()

                .and()
                .apply(jwtSecurityConfig);
    }
}