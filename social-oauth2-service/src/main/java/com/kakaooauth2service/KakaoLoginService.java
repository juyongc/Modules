package com.kakaooauth2service;


import com.kakaooauth2service.entity.UserEntity;
import com.kakaooauth2service.entity.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
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

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 인가코드로 엑세스토큰 받아오는 메서드
     */
    public String getAccessTokenFromKakao(String code) {

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("client_id", clientId);
        formData.add("redirect_uri", REDIRECT_URI);
        formData.add("code", code);

        KakaoTokenResponseDto kakaoTokenResponseDto = WebClient.create(KAUTH_TOKEN_URL_HOST).post()
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(BodyInserters.fromFormData(formData))
//            .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
//            .bodyValue("grant_type=authorization_code" +
//                "&client_id=" + clientId +
//                "&redirect_uri=" + REDIRECT_URI +
//                "&code=" + code)
            .retrieve()
            .bodyToMono(KakaoTokenResponseDto.class)
            .block();

        log.info("access token = " + kakaoTokenResponseDto.getAccessToken());
        UserEntity user = getUserInfoFromKakao(kakaoTokenResponseDto.getAccessToken());

        LoginJwtInputDto loginJwtInputDto = LoginJwtInputDto.builder()
            .id(user.getKakaoId())
            .profileImageUrl(user.getProfileImage())
            .thumbnailImageUrl(user.getThumbnailImage())
            .nickname(user.getNickname())
            .build();

        String token = jwtTokenProvider.createToken(loginJwtInputDto);

        log.info("token = " + token);

        return token;
    }

    /**
     * 유저 정보 가져오는 메서드
     */
    public UserEntity getUserInfoFromKakao(String accessToken) {

        String userInfoUrl = "https://kapi.kakao.com/v2/user/me";

        KakaoUserInfoResponseDto kakaoUserInfoResponseDto = WebClient.create(userInfoUrl).get()
            .headers(httpHeaders -> {
                httpHeaders.add("Authorization", "Bearer " + accessToken);
                httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
            })
            .retrieve()
            .bodyToMono(KakaoUserInfoResponseDto.class)
            .block();

        UserEntity userEntity = doKakaoAutoLogin(kakaoUserInfoResponseDto);

        return userEntity;
    }

    /**
     * 자동 로그인 & 회원가입 메서드
     *
     */
    public UserEntity doKakaoAutoLogin(KakaoUserInfoResponseDto responseDto) {

        String kakaoNickname = responseDto.getKakaoNickname();
        String thumbnailImageUrl = responseDto.getThumbnailImageUrl();
        String profileImageUrl = responseDto.getProfileImageUrl();

        log.info("kakaoUserInfoResponseDto = " + responseDto);
        log.info("kakaoNickname = " + kakaoNickname);
        log.info("thumbnailImageUrl = " + thumbnailImageUrl);
        log.info("profileImageUrl = " + profileImageUrl);


        Long kakaoId = responseDto.getId();

        Optional<UserEntity> user = userRepository.findById(kakaoId);

        if (user.isPresent()) {
            return user.get();
        }

        UserEntity userEntity = UserEntity.builder()
            .kakaoId(kakaoId)
            .nickname(responseDto.getKakaoNickname())
            .profileImage(responseDto.getProfileImageUrl())
            .thumbnailImage(responseDto.getThumbnailImageUrl()).build();
        userRepository.save(userEntity);

        return userEntity;
    }

}
