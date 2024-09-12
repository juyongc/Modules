package com.kakaooauth2service;

import io.netty.handler.codec.http.HttpHeaderValues;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoLoginService {


    @Value("${REST_API_KEY}")
    private String clientId;
    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String KAUTH_TOKEN_URL_HOST;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String REDIRECT_URI;

    public String getAccessTokenFromKakao(String code) {

        KakaoTokenResponseDto kakaoTokenResponseDto = WebClient.create(KAUTH_TOKEN_URL_HOST).post()
            .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
            .bodyValue("grant_type=authorization_code" +
                "&client_id=" + clientId +
                "&redirect_uri=" + REDIRECT_URI +
                "&code=" + code)
            .retrieve()
            .bodyToMono(KakaoTokenResponseDto.class)
            .block();

        log.info(String.valueOf(kakaoTokenResponseDto));

        return kakaoTokenResponseDto.getAccessToken();
    }

}
