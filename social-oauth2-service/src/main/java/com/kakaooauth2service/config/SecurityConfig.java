package com.kakaooauth2service.config;

import com.kakaooauth2service.JwtAuthFilter;
import com.kakaooauth2service.JwtTokenProvider;
import com.kakaooauth2service.entity.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // endpoint 별 권한 확인 부여
        // oauth2 로그인 페이지 및 다이렉트 uri 설정
        http
            .csrf((AbstractHttpConfigurer::disable))
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/view/**","/api/health/**", "/api/oauth/**", "/api/login/**", "/api/token/**", "/css/**", "/error/**", "/h2-console/**").permitAll()
                .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/view/login")
                .defaultSuccessUrl("/view/home", true)
            );

        http.addFilterBefore(new JwtAuthFilter(jwtTokenProvider,userRepository), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
