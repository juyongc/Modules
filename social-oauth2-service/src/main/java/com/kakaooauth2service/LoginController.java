package com.kakaooauth2service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

        String accessTokenFromKakao = kakaoLoginService.getAccessTokenFromKakao(code);

        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @PostMapping("/token/kakao")
//    public ResponseEntity<String> checkToken(@RequestHeader("Authorization") String authorizationHeader) {
//
//        String token = authorizationHeader.replace("Bearer ", "");
//
//        try {
//            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
//            String uid = decodedToken.getUid();
//            log.info("Received token for UID: " + uid);
//            return new ResponseEntity<>("Token is valid for UID: " + uid, HttpStatus.OK);
//        } catch (FirebaseAuthException e) {
//            return new ResponseEntity<>("Invalid token", HttpStatus.UNAUTHORIZED);
//        }
//    }
//
//    @GetMapping("/admin")
//    public ResponseEntity<?> admin(@RequestHeader("Authorization") String authorizationHeader) {
//
//        String token = authorizationHeader.replace("Bearer ", "");
//
//        try {
//            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
//            log.info("================================================================");
//            log.info(decodedToken.toString());
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (FirebaseAuthException e) {
//            return new ResponseEntity<>(new ModelAndView("unauthorized"), HttpStatus.UNAUTHORIZED);
//        }
//    }
//
//    /**
//     * 파이어베이스 refreshToken 갱신 시키는 기능
//     * - 기기 탈취 등으로 자동 access token 갱신을 막기 위함
//     */
//    @PostMapping("/forceout")
//    public ResponseEntity<?> forceOut(@RequestBody ForceoutRequestDto requestDto) {
//
//        String uid = requestDto.getUid();
//
//        if (uid == null) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        try {
//            FirebaseAuth.getInstance().revokeRefreshTokens(uid);
//            UserRecord user = FirebaseAuth.getInstance().getUser(uid);
//            long revocationSecond = user.getTokensValidAfterTimestamp() / 1000;
//            log.info("Tokens revoked at: " + revocationSecond);
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (FirebaseAuthException e) {
//            log.info(e.getMessage());
//            return new ResponseEntity<>(new ModelAndView("unauthorized"), HttpStatus.UNAUTHORIZED);
//        }
//    }
//
//    @Getter
//    @NoArgsConstructor
//    @AllArgsConstructor
//    private class ForceoutRequestDto {
//
//        private String uid;
//    }

}
