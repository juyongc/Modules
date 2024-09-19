package com.kakaooauth2service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class LoginController {

    private final KakaoLoginService kakaoLoginService;

    @GetMapping("/oauth/check")
    public ResponseEntity<?> kakaoLogin(@RequestParam("code") String code) {

        log.info(code);

        String token = kakaoLoginService.getAccessTokenFromKakao(code);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

}
