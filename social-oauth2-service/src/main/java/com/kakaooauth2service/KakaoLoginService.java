package com.kakaooauth2service;

import io.netty.handler.codec.http.HttpHeaderValues;
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
    private String KAUTH_USER_URL_HOST;

    public String getAccessTokenFromKakao(String code) {

        log.info("======================");
        log.info(KAUTH_TOKEN_URL_HOST);
        log.info(KAUTH_USER_URL_HOST);

        /**
         * TODO : 여기 코드 분석 필요
         */
        KakaoTokenResponseDto kakaoTokenResponseDto = WebClient.create(KAUTH_TOKEN_URL_HOST).post()
            .uri(uriBuilder -> uriBuilder
                .scheme("https")
                .path(KAUTH_USER_URL_HOST)
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", clientId)
                .queryParam("code", code)
                .build(true))
            .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
            .retrieve()
            //TODO : Custom Exception
//            .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Invalid Parameter")))
//            .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Internal Server Error")))
            .bodyToMono(KakaoTokenResponseDto.class)
            .block();

        return kakaoTokenResponseDto.getAccessToken();
    }

}
