package com.modules.oauth2.Config;

import com.modules.oauth2.FirebaseTokenFilter;
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
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // endpoint 별 권한 확인 부여
        // oauth2 로그인 페이지 및 다이렉트 uri 설정
        http
            .csrf((AbstractHttpConfigurer::disable))
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/health/**", "/oauth/**", "/login/**", "/token/**", "/css/**").permitAll()
//                    .requestMatchers("/admin/**").permitAll()
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/oauth")
                .defaultSuccessUrl("/oauth/home", true)
            );

        http.addFilterBefore(new FirebaseTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
